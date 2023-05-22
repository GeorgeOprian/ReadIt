package com.dis.readit.dtos.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class JudetDto implements Serializable {

	@JsonProperty("idJudet")
	private Integer idJudet;

	@JsonProperty("nume")
	private String nume;

}
