package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookRentRequestDto implements Serializable {

	@NotNull
	@JsonProperty("bookId")
	private Integer bookId;

	@NotNull
	@JsonProperty("userEmail")
	private String userEmail;

	@NotNull
	@JsonProperty("returnDate")
	private LocalDate returnDate;

}
