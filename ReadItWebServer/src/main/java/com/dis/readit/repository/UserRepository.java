package com.dis.readit.repository;

import com.dis.readit.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findUserByEmail(String email);
}
