package com.dis.readit.dtos.book;

import com.dis.readit.dtos.users.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BookReviewDto implements Serializable {

	@JsonProperty("user")
	private UserDto user;

	@JsonProperty("nbr_stars")
	private Integer nbrStars;

	@JsonProperty("content")
	private String content;

}
