package com.dis.readit.service.impl;

import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookListDto;
import com.dis.readit.dtos.book.BookUserRequestDto;
import com.dis.readit.exception.BookAlreadyInWishList;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.mapper.BookMapper;
import com.dis.readit.model.book.Book;
import com.dis.readit.model.user.DataBaseUser;
import com.dis.readit.model.user.WishList;
import com.dis.readit.repository.BookRepository;
import com.dis.readit.repository.UserRepository;
import com.dis.readit.repository.WishListRepository;
import com.dis.readit.service.UserLoaderService;
import com.dis.readit.service.WishListService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishListServiceImpl implements WishListService {

	private final BookRepository bookRepository;

	private final UserRepository userRepository;

	private final BookMapper bookMapper;

	private final UserLoaderService userLoaderService;

	private final WishListRepository wishListRepository;

	public WishListServiceImpl(BookRepository bookRepository, UserRepository userRepository, BookMapper bookMapper, UserLoaderService userLoaderService, WishListRepository wishListRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
		this.bookMapper = bookMapper;
		this.userLoaderService = userLoaderService;
		this.wishListRepository = wishListRepository;
	}

	@Override
	public BookDto addBookToWishList(BookUserRequestDto dto) {
		DataBaseUser user = userLoaderService.getUserByEmail(dto.getUserEmail());

		Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + dto.getBookId() + " was not found"));

		Book book = bookOpt.get();

		if (wishListRepository.getBookFromWishList(user.getUserId(), book.getBookId()).isPresent()) {
			throw new BookAlreadyInWishList("You already have this book in your wish list.");
		}

		user.addToWishList(book);

		userRepository.save(user);

		return bookMapper.mapToDto(book);
	}

	@Override
	public List<BookListDto> getWishList(String email) {
		DataBaseUser userByEmail = userLoaderService.getUserByEmail(email);

		Collection<WishList> wishList = wishListRepository.getWishListForUser(userByEmail.getUserId());

		return wishList.stream()
				.map(wishList1 -> bookMapper.mapToListDto(wishList1.getBook()))
				.collect(Collectors.toList());
	}

	@Override
	public void delete(BookUserRequestDto dto) {
		DataBaseUser user = userLoaderService.getUserByEmail(dto.getUserEmail());

		Optional<Book> bookOpt = bookRepository.findById(dto.getBookId());

		bookOpt.orElseThrow(() -> new EntityNotFound("Book with id " + dto.getBookId() + " was not found"));

		Book book = bookOpt.get();

		Optional<WishList> wishListBookOptional = wishListRepository.getBookFromWishList(user.getUserId(), book.getBookId());

		wishListBookOptional.orElseThrow(() -> new EntityNotFound("Book with id " + dto.getBookId() + " was not found"));

		wishListRepository.delete(wishListBookOptional.get());
	}
}
