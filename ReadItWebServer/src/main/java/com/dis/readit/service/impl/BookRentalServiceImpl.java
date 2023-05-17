package com.dis.readit.service.impl;

import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.service.BookRentalService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class BookRentalServiceImpl implements BookRentalService {

	private final BookRepository bookRepository;

	private final UserRepository userRepository;

	private final BookMapper bookMapper;

	public BookRentalServiceImpl(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.bookMapper = bookMapper;
	}

	@Override
	public BookRentResponseDto rentBook(BookRentRequestDto dto) {

		Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + dto.getBookId() + " was not found"));

		Optional<DataBaseUser> userOptional = userRepository.findUserByEmail(dto.getUserEmail());

		userOptional.orElseThrow(() -> new EntityNotFound("User with email " + dto.getUserEmail() + " was not found"));

		Book book = bookOpt.get();

		if (book.getInStock() == 0) {
			throw new EntityNotFound("There are no volumes available in stock for this book. Sorry to inform you.");
		}

		DataBaseUser user = userOptional.get();

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
