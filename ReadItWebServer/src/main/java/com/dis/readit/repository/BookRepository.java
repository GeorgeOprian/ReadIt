package com.dis.readit.repository;

import com.dis.readit.model.book.Book;
import com.dis.readit.model.user.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("SELECT b FROM Book b WHERE UPPER(b.title) like upper(concat('%', trim(?1), '%'))")
	Page<Book> findAllByTitle(String title, Pageable pageable);

	Optional<Book> findByIsbn(String isbn);

	@Transactional
	@Modifying
	@Query("delete from BookRental where book.bookId = ?1")
	void deleteBookFromRental(Integer bookId);

	@Transactional
	@Modifying
	@Query("delete from WishList where book.bookId = ?1")
	void deleteBookFromWishlist(Integer bookId);

	@Query("SELECT wl FROM WishList wl WHERE wl.user.userId = ?1 and wl.book.bookId = ?2")
	Optional<WishList> isInUserWishList(Integer userId, Integer bookId);
}
