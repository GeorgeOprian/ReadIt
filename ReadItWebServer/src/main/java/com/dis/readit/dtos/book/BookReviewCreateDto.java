package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookReviewCreateDto extends BookUserRequestDto {

	@JsonProperty("nbrStars")
	private Double nbrStars;

	@JsonProperty("content")
	private String content;

	@JsonProperty("reviewDate")
	private LocalDate reviewDate;

}
