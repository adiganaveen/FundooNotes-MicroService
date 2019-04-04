package com.bridgelabz.common.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomException() {
		super("OOPS! Something went wrong");
	}

	public CustomException(String errorMsg) {
		super(errorMsg);
	}

	public CustomException(String errorMsg, Throwable error) {
		super(errorMsg, error);
	}

}
