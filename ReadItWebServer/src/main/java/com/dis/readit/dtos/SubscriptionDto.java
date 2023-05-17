package com.dis.readit.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class SubscriptionDto {

	@JsonProperty("subscriptionId")
	private Integer subscriptionId;

	@NotNull
	@JsonProperty("startDate")
	private LocalDate startDate;

	@NotNull
	@JsonProperty("endDate")
	private LocalDate endDate;

	@NotNull
	@JsonProperty("userEmail")
	private String userEmail;
}
