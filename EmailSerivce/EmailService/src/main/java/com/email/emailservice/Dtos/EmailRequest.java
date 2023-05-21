package com.email.emailservice.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class EmailRequest implements Serializable {

	@JsonProperty("sender")
	private Long sender;

	@JsonProperty("recipients")
	private List<Long> recipients;

	@JsonProperty("emailSubject")
	private String emailSubject;

	@JsonProperty("emailBody")
	private String emailBody;


}
