package com.dis.readit.service.impl;

import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;
import com.dis.readit.exception.BookAlreadyRented;
import com.dis.readit.exception.BookAlreadyReturned;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.BookRental;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.BookRentalRepository;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.BookRentalService;
import com.dis.readit.service.UserLoaderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookRentalServiceImpl implements BookRentalService {

	private final BookRepository bookRepository;

	private final UserRepository userRepository;

	private final BookMapper bookMapper;

	private final UserLoaderService userLoaderService;

	private final BookRentalRepository rentalRepository;

	public BookRentalServiceImpl(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper, UserLoaderService userLoaderService, BookRentalRepository rentalRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.bookMapper = bookMapper;
		this.userLoaderService = userLoaderService;
		this.rentalRepository = rentalRepository;
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

		Optional<BookRental> bookRentedOpt = user.getBookRentals().stream().filter(bookRental -> Objects.equals(bookRental.getBook().getBookId(), book.getBookId())).findFirst();

		int rentedBookId = bookRentedOpt.isPresent() ? bookRentedOpt.get().getRentId(): 0;
		boolean rented = bookRentedOpt.isPresent() ? bookRentedOpt.get().isReturned(): false;

		return new BookRentResponseDto(rentedBookId, dto.getReturnDate(), rented, bookMapper.mapToDto(book));
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
				.map(bookRental -> new BookRentResponseDto(bookRental.getRentId(), bookRental.getReturnDate(), bookRental.isReturned(), bookMapper.mapToDto(bookRental.getBook())))
				.collect(Collectors.toList());
	}

	@Override
	public BookDto returnBook(Integer rentId) {
		Optional<BookRental> rentalOptional = rentalRepository.findById(rentId);

		rentalOptional.orElseThrow(() -> new EntityNotFound("There is no book rental with id " + rentId));

		BookRental bookRental = rentalOptional.get();

		if (bookRental.isReturned()){
			throw new BookAlreadyReturned("This book was already returned");
		}

		Book rentedBook = bookRental.getBook();

		rentedBook.setInStock(rentedBook.getInStock() + 1);

		bookRental.setReturned(true);

		Book updatedBook = saveReturnedBook(bookRental, rentedBook);

		return bookMapper.mapToDto(updatedBook);
	}

	@Transactional
	protected Book saveReturnedBook(BookRental bookRental, Book rentedBook) {
		rentalRepository.save(bookRental);

		return bookRepository.save(rentedBook);
	}


}
