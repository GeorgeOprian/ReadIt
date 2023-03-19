package com.dis.readit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

@ToString
@Entity
@Table(name = "book")
public class Book {

	@Id
	@Column(name = "isbn")
	private Long isbn;

	@Column(name = "name")
	private String name;

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
