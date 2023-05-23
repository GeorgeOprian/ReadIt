package com.dis.readit.service.impl;

import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;
import com.dis.readit.exception.BookAlreadyRented;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.book.BookRental;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.BookRentalService;
import com.dis.readit.service.UserLoaderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BookRentalServiceImpl implements BookRentalService {

	private final BookRepository bookRepository;

	private final UserRepository userRepository;

	private final BookMapper bookMapper;

	private final UserLoaderService userLoaderService;

	public BookRentalServiceImpl(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper, UserLoaderService userLoaderService) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.bookMapper = bookMapper;
		this.userLoaderService = userLoaderService;
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

		return new BookRentResponseDto(dto.getReturnDate(), bookMapper.mapToDto(book));
	}

	@Transactional void saveRental(Book book, DataBaseUser user) {

		userRepository.save(user);

		bookRepository.save(book);
	}
}
