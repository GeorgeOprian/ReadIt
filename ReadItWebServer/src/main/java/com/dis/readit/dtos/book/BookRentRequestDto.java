package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookRentRequestDto extends BookUserRequestDto {

	@NotNull
	@JsonProperty("returnDate")
	private LocalDate returnDate;

}
