package com.example.blps.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7843753478L;

	public ResourceAlreadyExistsException(String msg) {
		super(msg);
	}
}
