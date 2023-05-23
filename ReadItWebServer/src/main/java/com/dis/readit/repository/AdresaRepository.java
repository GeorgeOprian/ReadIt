package com.dis.readit.repository;

import com.dis.readit.model.address.Adresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdresaRepository extends JpaRepository<Adresa, Integer> {

	@Query(value = "select nextval ('read_it.adresa_seq') ", nativeQuery = true)
	Integer getNextId();

}
