package com.dis.readit.dtos.book;

import com.dis.readit.dtos.users.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookReviewDto implements Serializable {

	@JsonProperty("user")
	private UserDto user;

	@JsonProperty("bookId")
	private Integer bookId;

	@JsonProperty("nbrStars")
	private Integer nbrStars;

	@JsonProperty("content")
	private String content;

	@JsonProperty("reviewDate")
	private LocalDate reviewDate;

}
