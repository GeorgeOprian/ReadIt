package com.dis.readit.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	private Integer code;
	private String message;
}
