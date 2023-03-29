package com.dis.readit.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@Entity
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
	@Column(name = "description")
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

	@OneToMany
	@JoinColumn(name = "BOOK_ID", foreignKey = @ForeignKey(name = "FK_BOOK_CATEGORY"))
	private List<BookCategory> categories;

	@OneToOne
	@JoinColumn(name = "thumbnail_id")
	private BookThumbnail thumbnail;


}
