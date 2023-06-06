package com.dis.readit.service;

import com.dis.readit.dtos.PageDto;
import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookListDto;

import java.util.Map;

public interface BookService {

	BookDto insertBook(BookDto request);

	PageDto<BookListDto> loadListBooks(String query, Integer pageNumber, Integer pageSize, String sort);

	BookDto findBookById(String email, Integer bookId);

	BookDto updateBook(Integer bookId, Map<String, Object> body);

	void deleteBook(Integer bookId);

}
