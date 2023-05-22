package com.dis.readit.dtos.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {

	@JsonProperty("idAdresa")
	private Integer idAdresa;

	@JsonProperty("strada")
	private String strada;

	@JsonProperty("numar")
	private Integer numar;

	@JsonProperty("bloc")
	private String bloc;

	@JsonProperty("scara")
	private String scara;

	@JsonProperty("numarApartament")
	private Integer numarApartament;

}
