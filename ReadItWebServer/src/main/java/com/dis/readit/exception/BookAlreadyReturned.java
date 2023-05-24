package com.dis.readit.exception;

public class BookAlreadyReturned extends RuntimeException{

	public BookAlreadyReturned(String message) {
		super(message);
	}

}
