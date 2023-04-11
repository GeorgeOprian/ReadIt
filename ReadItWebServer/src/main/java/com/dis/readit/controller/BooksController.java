package com.dis.readit.controller;

import com.dis.readit.dtos.input.books.InputBookModel;
import com.dis.readit.dtos.output.PageDto;
import com.dis.readit.dtos.output.book.BookDto;
import com.dis.readit.dtos.output.book.BookListDto;
import com.dis.readit.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	// TODO gop 04.04.2023: find by filter
	@GetMapping("/all")
	public ResponseEntity<PageDto<BookListDto>> getAllBooks(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
													@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
													@RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy) {
		return ResponseEntity.ok(service.loadListBooks(pageNumber, pageSize, sortBy));
	}

	@GetMapping
	public ResponseEntity<BookDto> getAllBooks(@RequestParam(name = "bookId", defaultValue = "1") Integer bookId) {
		return ResponseEntity.ok(service.findBookById(bookId));
	}


	// TODO gop 04.04.2023: get one book

	// TODO gop 04.04.2023: update a book

	// TODO gop 04.04.2023: delete a book


}
