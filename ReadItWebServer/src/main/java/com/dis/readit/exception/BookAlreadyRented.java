package com.dis.readit.exception;

public class BookAlreadyRented extends RuntimeException {

	public BookAlreadyRented(String message) {
		super(message);
	}

}
