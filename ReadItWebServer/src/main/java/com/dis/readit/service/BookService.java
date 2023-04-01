package com.dis.readit.service;

import com.dis.readit.dtos.input.InputBookModel;
import com.dis.readit.dtos.output.BookDto;

import java.util.List;

public interface BookService {

	BookDto insertBook(InputBookModel inputBookModel);

	List<BookDto> loadAllBooks();
}
