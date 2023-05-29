package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BookUserRequestDto implements Serializable {

	@NotNull
	@JsonProperty("bookId")
	private Integer bookId;

	@NotNull
	@JsonProperty("userEmail")
	private String userEmail;
}
