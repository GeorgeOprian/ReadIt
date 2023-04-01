package com.dis.readit.exception;

public class BookAlreadyExistsException extends RuntimeException {

	public BookAlreadyExistsException(String message) {
		super(message);
	}
}
