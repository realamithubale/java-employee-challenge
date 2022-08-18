package com.example.rqchallenge.exceptionhandler;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
@Slf4j
public class EmployeeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { TooManyRequests.class })
	protected ResponseEntity<Object> handleTooManyRequestErrorHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_TOO_MANY_REQUEST_MESSAGE);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_TOO_MANY_REQUEST_MESSAGE, new HttpHeaders(),
				HttpStatus.TOO_MANY_REQUESTS, request);
	}

	@ExceptionHandler(value = { JsonMappingException.class })
	protected ResponseEntity<Object> JSONMappingErrorHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_JSON_MAPPING_MESSAGE);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_JSON_MAPPING_MESSAGE, new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { JsonProcessingException.class })
	protected ResponseEntity<Object> JSONProcessingErrorHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_JSON_PROCESSING_MESSAGE);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_JSON_PROCESSING_MESSAGE, new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { IOException.class })
	protected ResponseEntity<Object> IOExceptionHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_IO_EXCEPTION_MESSAGE);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_IO_EXCEPTION_MESSAGE, new HttpHeaders(),
				HttpStatus.SERVICE_UNAVAILABLE, request);
	}

	@ExceptionHandler(value = { EmployeeInputException.class })
	protected ResponseEntity<Object> EmployeeInputExceptionHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_EMPLOYEE_INPUT_MESSAGE);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_EMPLOYEE_INPUT_MESSAGE, new HttpHeaders(),
				HttpStatus.EXPECTATION_FAILED, request);
	}

	@ExceptionHandler(value = { InternalServerError.class })
	protected ResponseEntity<Object> InternalServerErrorHandler(RuntimeException ex, WebRequest request) {
		log.error(EmployeeConstants.ERROR_INTERNAL_SERVER);
		return handleExceptionInternal(ex, EmployeeConstants.ERROR_INTERNAL_SERVER, new HttpHeaders(),
				HttpStatus.EXPECTATION_FAILED, request);
	}

}
