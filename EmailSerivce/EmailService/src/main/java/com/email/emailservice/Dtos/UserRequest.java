package com.email.emailservice.Dtos;

import lombok.Data;

@Data
public class UserRequest {

	private Integer operation;

	private Long id;

	private String userName;

	private String emailAddress;
}
