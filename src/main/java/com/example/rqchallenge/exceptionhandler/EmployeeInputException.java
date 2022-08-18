package com.example.rqchallenge.exceptionhandler;

public class EmployeeInputException extends RuntimeException {

	public EmployeeInputException(String errorMessage) {
		super(errorMessage);
	}
}
