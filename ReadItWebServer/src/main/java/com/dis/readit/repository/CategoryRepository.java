package com.dis.readit.repository;

import com.dis.readit.model.book.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByCategoryName(String categoryName);
}
