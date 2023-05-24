package com.dis.readit.controller.advice;

import com.dis.readit.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleDupicateObject(Exception e) {
		log.debug("Duplicate object... ");
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorResponse.builder()
						.code(HttpStatus.CONFLICT.value())
						.message(e.getMessage())
						.build());
	}


	@ExceptionHandler(EntityNotFound.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFound(Exception e) {
		log.debug("Object not found... ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.builder()
						.code(HttpStatus.NOT_FOUND.value())
						.message(e.getMessage())
						.build());
	}


	@ExceptionHandler(ExpiredSubscription.class)
	public ResponseEntity<ErrorResponse> handleExpiredSubscription(Exception e) {
		log.debug("Expired subscription ");
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ErrorResponse.builder()
						.code(HttpStatus.FORBIDDEN.value())
						.message(e.getMessage())
						.build());
	}


	@ExceptionHandler(BookAlreadyRented.class)
	public ResponseEntity<ErrorResponse> handleAlreadyRentedBook(Exception e) {
		log.debug("Book already rented ");
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ErrorResponse.builder()
						.code(HttpStatus.FORBIDDEN.value())
						.message(e.getMessage())
						.build());
	}

	@ExceptionHandler(BookAlreadyReturned.class)
	public ResponseEntity<ErrorResponse> handleAlreadyReturnedBook(Exception e) {
		log.debug("Book already returned ");
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(ErrorResponse.builder()
						.code(HttpStatus.FORBIDDEN.value())
						.message(e.getMessage())
						.build());
	}
}
