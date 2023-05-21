package com.email.emailservice.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserEmailResponse {

	private String subject;

	private String content;

	private List<String> emailAddresses;

}
