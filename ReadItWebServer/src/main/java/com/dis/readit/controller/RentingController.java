package com.dis.readit.controller;

import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;
import com.dis.readit.service.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookrental")
public class RentingController {
	@Autowired
	private BookRentalService service;


	@PostMapping("/rent")
	public ResponseEntity<BookRentResponseDto> createSubscription(@RequestBody BookRentRequestDto dto) {
		return ResponseEntity.ok(service.rentBook(dto));
	}

	// TODO gop 04.04.2023: return a book

	// TODO gop 23.05.2023: get user rentals
//	@GetMapping("/getall")
//	public ResponseEntity<List>

}
