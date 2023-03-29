package com.dis.readit.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ImageLinks implements Serializable {


	@JsonProperty("smallThumbnail")
	private String smallThumbnail;
	@JsonProperty("thumbnail")
	private String thumbnail;

	public String getSmallThumbnail() {
		return smallThumbnail;
	}

	public void setSmallThumbnail(String smallThumbnail) {
		this.smallThumbnail = smallThumbnail;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

}
