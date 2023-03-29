package com.dis.readit.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book_category")
public class BookCategory {

	@Id
	@Column(name ="category_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	@Column(name = "category")
	private String category;


}
