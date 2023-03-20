package com.dis.readit.controller;

import com.dis.readit.dtos.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {

	@GetMapping
	public Test getBooks(){
		return new Test("test");
	}

}
