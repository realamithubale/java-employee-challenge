package com.example.rqchallenge.exceptionhandler;

public class ExternalServiceException extends RuntimeException {

	public ExternalServiceException(String errorMessage) {
		super(errorMessage);
	}
}
