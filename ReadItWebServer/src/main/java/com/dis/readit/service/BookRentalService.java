package com.dis.readit.service;

import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;

import java.util.List;

public interface BookRentalService {
	BookRentResponseDto rentBook(BookRentRequestDto dto);

	List<BookRentResponseDto> loadRentedBooks(String email, boolean returned);

	List<BookRentResponseDto> returnBook(Integer rentId);
}
