package com.dis.readit.controller;

import com.dis.readit.dtos.input.InputBookModel;
import com.dis.readit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
