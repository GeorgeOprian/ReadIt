package com.dis.readit.controller;

import com.dis.readit.dtos.book.BookReviewCreateDto;
import com.dis.readit.dtos.book.BookReviewDto;
import com.dis.readit.service.BookReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

	private final BookReviewService service;

	public ReviewsController(BookReviewService service) {
		this.service = service;
	}

	@PostMapping("/add")
	private ResponseEntity<BookReviewDto> addReview(@RequestBody BookReviewCreateDto requestDto) {
		return ResponseEntity.ok(service.addReview(requestDto));
	}


	@GetMapping
	private ResponseEntity<List<BookReviewDto>> getReviews(@RequestParam(name = "bookId") Integer bookId) {
		return ResponseEntity.ok(service.getBookReviews(bookId));
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteBook(@RequestParam(name = "idReview") Integer idReview) {

		service.deleteReview(idReview);

		return ResponseEntity.noContent().build();
	}
}
