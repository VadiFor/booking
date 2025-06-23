package com.learn.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IncorrectDataException extends RuntimeException {
	public IncorrectDataException(String message) {
		super(message);
	}
}
