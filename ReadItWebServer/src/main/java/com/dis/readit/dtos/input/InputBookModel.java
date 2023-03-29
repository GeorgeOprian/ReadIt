package com.dis.readit.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InputBookModel {

	@JsonProperty("item")
	private Item item;

	@JsonProperty("inStock")
	private Integer inStock;
}
