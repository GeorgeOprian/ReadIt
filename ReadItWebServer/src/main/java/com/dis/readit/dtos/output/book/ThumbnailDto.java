package com.dis.readit.dtos.output.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ThumbnailDto implements Serializable {
	@JsonProperty("smallThumbnail")
	private String smallThumbnail;

	@JsonProperty("thumbnail")
	private String thumbnail;

}
