package com.dis.readit.repository;

import com.dis.readit.model.user.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

	@Query("SELECT wl FROM WishList wl WHERE wl.user.userId = ?1 and wl.book.bookId = ?2")
	Optional<WishList> getBookFromWishList(Integer userId, Integer bookId);

	@Query("SELECT wl FROM WishList wl WHERE wl.user.userId = ?1")
	Collection<WishList> getWishListForUser(Integer userId);
}
