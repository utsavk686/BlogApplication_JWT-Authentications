package com.wipro.exceptions;

public class BlogNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BlogNotFoundException() {

	}

	public BlogNotFoundException(String message) {
		super(message);
	}
}