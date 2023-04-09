package com.dis.readit.service;

import com.dis.readit.dtos.input.books.InputBookModel;
import com.dis.readit.dtos.output.PageDto;
import com.dis.readit.dtos.output.book.BookDto;

public interface BookService {

	BookDto insertBook(InputBookModel inputBookModel);

	PageDto<BookDto> loadBooks(Integer pageNumber, Integer pageSize, String sort);
}
