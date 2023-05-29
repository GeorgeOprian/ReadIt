package com.dis.readit.service;

import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookListDto;
import com.dis.readit.dtos.book.BookUserRequestDto;

import java.util.List;

public interface WishListService {
	BookDto addBookToWishList(BookUserRequestDto requestDto);

	void delete(BookUserRequestDto requestDto);

	List<BookListDto> getWishList(String email);
}
