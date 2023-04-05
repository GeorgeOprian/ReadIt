package com.dis.readit.dtos.input.books;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SearchInfo implements Serializable {

	@JsonProperty("textSnippet")
	private String textSnippet;

	public String getTextSnippet() {
		return textSnippet;
	}

	public void setTextSnippet(String textSnippet) {
		this.textSnippet = textSnippet;
	}

}