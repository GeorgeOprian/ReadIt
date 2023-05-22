package com.dis.readit.dtos.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class LocalitateDto {

	@JsonProperty("idLocalitate")
	private Integer idLocalitate;

	@JsonProperty("nume")
	private String nume;

	@JsonProperty("idJudet")
	private Integer idJudet;
}
