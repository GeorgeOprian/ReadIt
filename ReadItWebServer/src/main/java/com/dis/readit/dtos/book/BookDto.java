package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookDto extends BookListDto {

	@JsonProperty("publisher")
	private String publisher;

	@JsonProperty("publishedDate")
	private String publishedDate;
	@JsonProperty("description")
	private String description;

	@JsonProperty("isbn")
	private String isbn; //industryIdentifiers[0]
	@JsonProperty("pageCount")
	private Integer pageCount;

	@JsonProperty("maturityRating")
	private String maturityRating;

	@JsonProperty("language")
	private String language;

	@JsonProperty("inStock")
	private Integer inStock;

}
