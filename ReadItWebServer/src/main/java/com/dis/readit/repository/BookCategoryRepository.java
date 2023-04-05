package com.dis.readit.repository;

import com.dis.readit.model.book.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategory.BookCategoryId> {
}
