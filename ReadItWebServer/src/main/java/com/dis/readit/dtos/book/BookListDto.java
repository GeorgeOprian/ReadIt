package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookListDto implements Serializable {

	@NotNull
	@JsonProperty("bookId")
	private Integer bookId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("author")
	private String author;

	@JsonProperty("averageRating")
	private Double averageRating;
	@JsonProperty("ratingsCount")
	private Integer ratingsCount;

	@JsonProperty("thumbnail")
	private ThumbnailDto thumbnail;

	@JsonProperty("categories")
	private List<CategoryDto> categories = new ArrayList<>();

}
