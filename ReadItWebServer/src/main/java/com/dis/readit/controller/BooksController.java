package com.dis.readit.controller;

import com.dis.readit.dtos.input.books.InputBookModel;
import com.dis.readit.dtos.output.PageDto;
import com.dis.readit.dtos.output.book.BookDto;
import com.dis.readit.dtos.output.book.BookListDto;
import com.dis.readit.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

	private final BookService service;

	public BooksController(BookService service) {
		this.service = service;
	}

	@PostMapping("/addBook")
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto request) {
		return ResponseEntity.ok(service.insertBook(request));
	}

	@GetMapping("/all")
	public ResponseEntity<PageDto<BookListDto>> getAllBooks(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
													@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
													@RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy) {
		return ResponseEntity.ok(service.loadListBooks(pageNumber, pageSize, sortBy));
	}

	@GetMapping
	public ResponseEntity<BookDto> getById(@RequestParam(name = "bookId", defaultValue = "1") Integer bookId) {
		return ResponseEntity.ok(service.findBookById(bookId));
	}
	@PatchMapping(path = "/updatebook/{bookId}")
	public ResponseEntity<BookDto> updateBook(@PathVariable(name = "bookId") Integer bookId, @RequestBody Map<String, Object> body) {
		return ResponseEntity.ok(service.updateBook(bookId, body));
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteBook(@RequestParam(name = "bookId", defaultValue = "1") Integer bookId) {

		service.deleteBook(bookId);

		return ResponseEntity.noContent().build();
	}



}
