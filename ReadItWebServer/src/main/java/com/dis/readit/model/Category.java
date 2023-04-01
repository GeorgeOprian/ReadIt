package com.dis.readit.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@Entity(name = "Category")
@Table(name = "category", uniqueConstraints = @UniqueConstraint(name = "UK_CATEGORY", columnNames = "CATEGORY_NAME"))
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Category {

	@Id
	@Column(name ="category_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	@Column(name = "category_name")
	private String categoryName;

	@OneToMany(
			mappedBy = "category",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.LAZY
	)
	private List<BookCategory> bookCategories = new ArrayList<>();

	public Category(String categoryName) {
		this.categoryName = categoryName;
	}
}
