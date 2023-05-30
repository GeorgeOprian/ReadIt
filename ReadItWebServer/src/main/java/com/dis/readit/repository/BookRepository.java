package com.dis.readit.repository;

import com.dis.readit.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findByIsbn(String isbn);

	@Transactional
	@Modifying
	@Query("delete from BookRental where book.bookId = ?1")
	void deleteBookFromRental(Integer bookId);

	@Transactional
	@Modifying
	@Query("delete from WishList where book.bookId = ?1")
	void deleteBookFromWishlist(Integer bookId);

}
