package com.dis.readit.repository;

import com.dis.readit.model.book.BookThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookThumbnailRepository extends JpaRepository<BookThumbnail, Integer> {
}
