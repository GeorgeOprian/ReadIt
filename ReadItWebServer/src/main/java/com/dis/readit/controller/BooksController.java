package com.dis.readit.controller;

import com.dis.readit.dtos.input.InputBookModel;
import com.dis.readit.dtos.output.BookDto;
import com.dis.readit.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

	private final BookService service;

	public BooksController(BookService service) {
		this.service = service;
	}

	@PostMapping("/addBook")
	public ResponseEntity<BookDto> addBook(@RequestBody InputBookModel request) {
		return ResponseEntity.ok(service.insertBook(request));
	}

	@GetMapping("/all")
	public ResponseEntity<List<BookDto>> getAllBooks() {
		return ResponseEntity.ok(service.loadAllBooks());
	}

	// TODO gop 04.04.2023: find by filter

	// TODO gop 04.04.2023: get one book

	// TODO gop 04.04.2023: update a book

	// TODO gop 04.04.2023: delete a book


}
