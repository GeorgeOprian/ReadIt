package com.dis.readit.service;

import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;

public interface BookRentalService {
	BookRentResponseDto rentBook(BookRentRequestDto dto);
}
