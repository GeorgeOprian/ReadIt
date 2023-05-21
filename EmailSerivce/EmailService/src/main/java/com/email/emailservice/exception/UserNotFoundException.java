package com.email.emailservice.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
