package com.dis.readit.exception;

public class ExpiredSubscription extends RuntimeException{

	public ExpiredSubscription(String message) {
		super(message);
	}
}
