package com.email.emailservice.controller.advice;

import com.email.emailservice.exception.ErrorResponse;
import com.email.emailservice.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleProductNotFound(Exception e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorResponse.builder()
						.code(404)
						.message(e.getMessage())
						.build());
	}
}
