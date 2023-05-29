package com.dis.readit.exception;

public class BookAlreadyInWishList extends RuntimeException{
	public BookAlreadyInWishList(String message) {
		super(message);
	}
}
