package com.dis.readit.repository;

import com.dis.readit.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

	Optional<Book> findByIsbn(String isbn);

}
