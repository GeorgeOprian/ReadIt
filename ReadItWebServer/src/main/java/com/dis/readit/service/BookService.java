package com.dis.readit.service;

import com.dis.readit.dtos.input.books.InputBookModel;
import com.dis.readit.dtos.output.PageDto;
import com.dis.readit.dtos.output.book.BookDto;
import com.dis.readit.dtos.output.book.BookListDto;

import java.util.Map;

public interface BookService {

	BookDto insertBook(InputBookModel inputBookModel);

	PageDto<BookListDto> loadListBooks(Integer pageNumber, Integer pageSize, String sort);

	BookDto findBookById(Integer bookId);

	BookDto updateBook(Integer bookId, Map<String, Object> body);

	void deleteBook(Integer bookId);
}
