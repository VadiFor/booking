package com.learn.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookingOverlapException extends RuntimeException {
	public BookingOverlapException(String message) {
		super(message);
	}
}
