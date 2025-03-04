package com.dis.readit.service.impl;

import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;
import com.dis.readit.dtos.book.CategoryDto;
import com.dis.readit.exception.BookAlreadyRented;
import com.dis.readit.exception.BookAlreadyReturned;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.BookRental;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.rabbitmq.RabbitMQMessageProducer;
import com.dis.readit.rabbitmq.requests.EmailRequest;
import com.dis.readit.repository.BookRentalRepository;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.BookRentalService;
import com.dis.readit.service.RabbitMQService;
import com.dis.readit.service.UserLoaderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookRentalServiceImpl implements BookRentalService {

	private final BookRepository bookRepository;

	private final UserRepository userRepository;

	private final BookMapper bookMapper;

	private final UserLoaderService userLoaderService;

	private final BookRentalRepository rentalRepository;

	private final RabbitMQService rabbitMQService;

	public BookRentalServiceImpl(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper, UserLoaderService userLoaderService, BookRentalRepository rentalRepository,
			RabbitMQService rabbitMQService) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.bookMapper = bookMapper;
		this.userLoaderService = userLoaderService;
		this.rentalRepository = rentalRepository;
		this.rabbitMQService = rabbitMQService;
	}

	@Override
	public BookRentResponseDto rentBook(BookRentRequestDto dto) {

		Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + dto.getBookId() + " was not found"));

		Book book = bookOpt.get();

		if (book.getInStock() == 0) {
			throw new EntityNotFound("There are no volumes available in stock for this book. Sorry to inform you.");
		}

		DataBaseUser user = userLoaderService.getUserByEmail(dto.getUserEmail());

		Optional<BookRental> alreadyRentedBook = user.getBookRentals().stream()
				.filter(rentedBook -> !rentedBook.isReturned()
						&& rentedBook.getBook().getBookId().equals(book.getBookId()))
				.findFirst();

		if (alreadyRentedBook.isPresent()) {
			throw new BookAlreadyRented("This book was already rented by the same user");
		}

		user.addRental(book, dto.getReturnDate());

		book.setInStock(book.getInStock() - 1);

		saveRental(book, user);

		sendEmailToUser(user, book.getTitle());

		Optional<BookRental> bookRentedOpt = user.getBookRentals().stream().filter(bookRental -> Objects.equals(bookRental.getBook().getBookId(), book.getBookId())).findFirst();

		int rentedBookId = bookRentedOpt.isPresent() ? bookRentedOpt.get().getRentId(): 0;
		boolean rented = bookRentedOpt.isPresent() ? bookRentedOpt.get().isReturned(): false;

		return new BookRentResponseDto(rentedBookId, dto.getReturnDate(), rented, bookMapper.mapToDto(book));
	}

	private void sendEmailToUser(DataBaseUser user, String title) {
		DataBaseUser adminUser =  userLoaderService.getUserByEmail(DataBaseUser.ADMIN_USER_EMAIL);
		String subject = "New ReadIt Rental";
		String emailBody = "Hi " + user.getUserName() + ",\n\nYour book, " + title + " is on the way. You will be contacted by the courier soon to let you know more details about the package.";

		EmailRequest emailRequest = EmailRequest.createEmailForUser(adminUser.getUserId(), Arrays.asList(user.getUserId()), subject, emailBody);

		rabbitMQService.sendMessageToEmailService(RabbitMQMessageProducer.EMAIL_ROUTING_KEY, emailRequest);
	}

	@Transactional
	void saveRental(Book book, DataBaseUser user) {

		userRepository.save(user);

		bookRepository.save(book);
	}

	@Override
	public List<BookRentResponseDto> loadRentedBooks(String email, boolean returned) {
		DataBaseUser userByEmail = userLoaderService.getUserByEmail(email);

		Collection<BookRental> bookRentals = rentalRepository.findByUserIdReturned(userByEmail.getUserId(), returned);

		return bookRentals.stream()
				.map(bookRental -> {
					Book book = bookRental.getBook();
					book.getBookCategories();
					BookDto bookDto = bookMapper.mapToDto(book);

					bookDto.setCategories(book.getBookCategories().stream().map(categ -> new CategoryDto(categ.getCategory().getCategoryName())).collect(Collectors.toList()));

					return new BookRentResponseDto(bookRental.getRentId(), bookRental.getReturnDate(), bookRental.isReturned(), bookDto);
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<BookRentResponseDto> returnBook(Integer rentId) {
		Optional<BookRental> rentalOptional = rentalRepository.findById(rentId);

		rentalOptional.orElseThrow(() -> new EntityNotFound("There is no book rental with id " + rentId));

		BookRental bookRental = rentalOptional.get();

		if (bookRental.isReturned()){
			throw new BookAlreadyReturned("This book was already returned");
		}

		Book rentedBook = bookRental.getBook();

		rentedBook.setInStock(rentedBook.getInStock() + 1);

		bookRental.setReturned(true);

		saveReturnedBook(bookRental, rentedBook);

		DataBaseUser adminUser =  userLoaderService.getUserByEmail(DataBaseUser.ADMIN_USER_EMAIL);
		String subject = "ReadIt Book Return";
		String emailBody = "Hi " + bookRental.getUser().getUserName() + ",\n\nYou can now return your book " + rentedBook.getTitle() +". It you will be contacted by the courier soon to let you know more details about he package.";

		EmailRequest emailRequest = EmailRequest.createEmailForUser(adminUser.getUserId(), Arrays.asList(bookRental.getUser().getUserId()), subject, emailBody);

		rabbitMQService.sendMessageToEmailService(RabbitMQMessageProducer.EMAIL_ROUTING_KEY, emailRequest);

		Collection<BookRental> returnedBooks = rentalRepository.findByUserIdReturned(bookRental.getUser().getUserId(), true);

		return returnedBooks.stream()
				.map(b -> new BookRentResponseDto(b.getRentId(), b.getReturnDate(), b.isReturned(), bookMapper.mapToDto(b.getBook())))
				.collect(Collectors.toList());
	}

	@Transactional
	protected Book saveReturnedBook(BookRental bookRental, Book rentedBook) {
		rentalRepository.save(bookRental);

		return bookRepository.save(rentedBook);
	}

}
