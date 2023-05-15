package com.dis.readit.repository;

import com.dis.readit.model.user.DataBaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DataBaseUser, Integer> {
	Optional<DataBaseUser> findUserByEmail(String email);
}
