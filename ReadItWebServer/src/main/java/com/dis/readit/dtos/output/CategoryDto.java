package com.dis.readit.dtos.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CategoryDto implements Serializable {

	@JsonProperty("categoryName")
	private String categoryName;


}
