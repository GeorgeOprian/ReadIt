package com.dis.readit.repository;

import com.dis.readit.model.book.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface BookRentalRepository extends JpaRepository<BookRental, Integer> {

	@Query("SELECT br FROM BookRental br WHERE br.user.userId = ?1 and br.returned = ?2")
	Collection<BookRental> findByUserIdReturned(Integer userId, boolean returned);

}
