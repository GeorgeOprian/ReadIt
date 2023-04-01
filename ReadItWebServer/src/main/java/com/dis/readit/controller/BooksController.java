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
	public String addBook(@RequestBody InputBookModel request) {

		service.insertBook(request);

		return "test";
	}

	@GetMapping("/all")
	public ResponseEntity<List<BookDto>> getAllBooks() {
		return ResponseEntity.ok(service.loadAllBooks());
	}

}
