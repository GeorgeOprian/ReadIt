package com.dis.readit.dtos.output.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BookListDto implements Serializable {

	@NotNull
	@JsonProperty("bookId")
	private Integer bookId;

	@JsonProperty("title")
	private String title;

	@JsonProperty( "author")
	private String author;

	@JsonProperty( "averageRating")
	private Double averageRating;
	@JsonProperty( "ratingsCount")
	private Integer ratingsCount;

	@JsonProperty("thumbnail")
	private ThumbnailDto thumbnail;

	@JsonProperty("categories")
	private List<CategoryDto> categories;

//	@JsonProperty("inStock")
//	private Integer inStock;
}
