package com.dis.readit.controller;

import com.dis.readit.dtos.book.BookDto;
import com.dis.readit.dtos.book.BookListDto;
import com.dis.readit.dtos.book.BookUserRequestDto;
import com.dis.readit.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

	private final WishListService service;

	public WishListController(WishListService service) {
		this.service = service;
	}

	@PostMapping("/addbook")
	private ResponseEntity<BookDto> addBookToWishList(@RequestBody BookUserRequestDto requestDto) {
		return ResponseEntity.ok(service.addBookToWishList(requestDto));
	}


	@GetMapping
	private ResponseEntity<List<BookListDto>> getWishList(@RequestParam(name = "email") String email) {
		return ResponseEntity.ok(service.getWishList(email));
	}

	@DeleteMapping("/deletebook")
	public ResponseEntity<List<BookListDto>> deleteBook(@RequestParam(name = "bookId") Integer bookId, @RequestParam(name = "email") String email) {
		BookUserRequestDto requestDto = new BookUserRequestDto();
		requestDto.setBookId(bookId);
		requestDto.setUserEmail(email);
		return ResponseEntity.ok(service.deleteBook(requestDto));
	}

}
