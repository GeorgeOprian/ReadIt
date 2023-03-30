package com.dis.readit.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "Book")
@Table(name = "book")
public class Book {

	@Id
	@Column(name ="bookId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;

	@Column(name ="title")
	private String title;
	
	@Column(name = "author")
	private String author;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "publishedDate")
	private String publishedDate;
	@Column(name = "description", length = 2048)
	private String description;

	@Column(name = "isbn")
	private String isbn; //industryIdentifiers[0]

	@Column(name = "pageCount")
	private Integer pageCount;
	@Column(name = "averageRating")
	private Double averageRating;
	@Column(name = "ratingsCount")
	private Integer ratingsCount;
	@Column(name = "maturityRating")
	private String maturityRating;

	@Column(name = "language")
	private String language;

	@Column(name = "in_stock")
	private Integer inStock;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "thumbnail_id")
	private BookThumbnail thumbnail;

	@OneToMany(
			mappedBy = "book",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<BookCategory> bookCategories = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		Book book = (Book) o;
		return getBookId() != null && Objects.equals(getBookId(), book.getBookId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public void addCategory(Category category) {
		BookCategory bookCategory = new BookCategory(this, category);
		bookCategories.add(bookCategory);
		category.getBookCategories().add(bookCategory);
	}

}
