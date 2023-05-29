package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookReviewCreateDto extends BookUserRequestDto {

	@JsonProperty("nbrStars")
	private Integer nbrStars;

	@JsonProperty("content")
	private String content;

}
