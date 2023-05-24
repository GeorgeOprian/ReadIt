package com.dis.readit.dtos.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor @NoArgsConstructor
public class BookRentResponseDto implements Serializable {

	@JsonProperty("rentId")
	private Integer rentId;

	@JsonProperty("returnDate")
	private LocalDate returnDate;

	@JsonProperty("returned")
	private Boolean returned;

	@JsonProperty("rentedBook")
	private BookDto book;

}
