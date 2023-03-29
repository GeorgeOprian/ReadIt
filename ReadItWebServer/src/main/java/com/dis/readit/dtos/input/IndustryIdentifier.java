package com.dis.readit.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class IndustryIdentifier implements Serializable {

	@JsonProperty("type")
	private String type;
	@JsonProperty("identifier")
	private String identifier;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
