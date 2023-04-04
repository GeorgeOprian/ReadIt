package com.dis.readit.dtos.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookDto implements Serializable {
	@JsonProperty("bookId")
	private Integer bookId;

	@JsonProperty("title")
	private String title;

	@JsonProperty( "author")
	private String author;

	@JsonProperty( "publisher")
	private String publisher;

	@JsonProperty( "publishedDate")
	private String publishedDate;
	@JsonProperty( "description")
	private String description;

	@JsonProperty( "isbn")
	private String isbn; //industryIdentifiers[0]

	@JsonProperty( "pageCount")
	private Integer pageCount;
	@JsonProperty( "averageRating")
	private Double averageRating;
	@JsonProperty( "ratingsCount")
	private Integer ratingsCount;
	@JsonProperty( "maturityRating")
	private String maturityRating;

	@JsonProperty( "language")
	private String language;

	@JsonProperty( "inStock")
	private Integer inStock;

	// TODO gop 01.04.2023: vezi userMapper
	@JsonProperty("thumbnail")
	private ThumbnailDto thumbnail;

	@JsonProperty("categories")
	private List<CategoryDto> categories;
}
