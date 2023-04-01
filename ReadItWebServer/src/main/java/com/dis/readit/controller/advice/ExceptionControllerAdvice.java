package com.dis.readit.controller.advice;

import com.dis.readit.exception.BookAlreadyExistsException;
import com.dis.readit.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler({ BookAlreadyExistsException.class})
	public ResponseEntity<ErrorResponse> handleProductNotFound(Exception e) {
		log.debug("Product not found... ");
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorResponse.builder()
						.code(409)
						.message(e.getMessage())
						.build());
	}
}
