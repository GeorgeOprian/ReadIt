package com.dis.readit.service;

import com.dis.readit.dtos.input.books.InputBookModel;
import com.dis.readit.dtos.output.PageDto;
import com.dis.readit.dtos.output.book.BookDto;
import com.dis.readit.dtos.output.book.BookListDto;

public interface BookService {

	BookDto insertBook(InputBookModel inputBookModel);

	PageDto<BookListDto> loadListBooks(Integer pageNumber, Integer pageSize, String sort);

	BookDto findBookById(Integer bookId);
}
