package com.dis.readit.dtos.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class ThumbNailDto implements Serializable {
	@JsonProperty("smallThumbnail")
	private String smallThumbnail;

	@JsonProperty("thumbnail")
	private String thumbnail;

}
