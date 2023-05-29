package com.dis.readit.service;

import com.dis.readit.dtos.book.BookReviewCreateDto;
import com.dis.readit.dtos.book.BookReviewDto;

import java.util.List;

public interface BookReviewService {
	BookReviewDto addReview(BookReviewCreateDto requestDto);

	List<BookReviewDto> getBookReviews(Integer bookId);

	void deleteReview(Integer idReview);
}
