package com.dis.readit.controller;

import com.dis.readit.dtos.input.InputBookModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {


	@PostMapping("/addBook")
	public String addBook(@RequestBody InputBookModel request) {


		return "test";
	}

}
