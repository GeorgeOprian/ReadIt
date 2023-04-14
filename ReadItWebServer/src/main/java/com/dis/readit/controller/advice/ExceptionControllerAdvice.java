package com.dis.readit.controller.advice;

import com.dis.readit.exception.BookAlreadyExistsException;
import com.dis.readit.exception.EntityNotFound;
import com.dis.readit.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(BookAlreadyExistsException.class)
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
}
