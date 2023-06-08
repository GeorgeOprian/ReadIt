package com.dis.readit.controller;

import com.dis.readit.dtos.book.BookRentRequestDto;
import com.dis.readit.dtos.book.BookRentResponseDto;
import com.dis.readit.service.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookrental")
public class RentingController {
	@Autowired
	private BookRentalService service;

	@PostMapping("/rent")
	public ResponseEntity<BookRentResponseDto> rentBook(@RequestBody BookRentRequestDto dto) {
		return ResponseEntity.ok(service.rentBook(dto));
	}

	@PostMapping("/return")
	public ResponseEntity<List<BookRentResponseDto>> returnABook(@RequestParam(name = "rentId") Integer rentId) {
		return ResponseEntity.ok(service.returnBook(rentId));
	}

	@GetMapping("/notreturnedbooks")
	public ResponseEntity<List<BookRentResponseDto>> loadNotReturnedBooks(@RequestParam(name = "email") String email) {
		return ResponseEntity.ok(service.loadRentedBooks(email, false));
	}

	@GetMapping("/returnedbooks")
	public ResponseEntity<List<BookRentResponseDto>> loadReturnedBooks(@RequestParam(name = "email") String email) {
		return ResponseEntity.ok(service.loadRentedBooks(email, true));
	}

}
