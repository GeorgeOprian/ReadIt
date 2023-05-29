package com.dis.readit.repository;

import com.dis.readit.model.book.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {

	@Query("SELECT rw FROM BookReview rw WHERE rw.book.bookId = ?1")
	Collection<BookReview> getReviewsForBook(Integer bookId);
}
