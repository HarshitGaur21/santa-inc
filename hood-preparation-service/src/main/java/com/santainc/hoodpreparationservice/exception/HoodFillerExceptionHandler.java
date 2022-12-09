package com.santainc.hoodpreparationservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.santainc.hoodpreparationservice.bean.ExceptionDetails;

@ControllerAdvice
public class HoodFillerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionDetails> handleAllExceptions(Exception ex, WebRequest request)
			throws Exception {
		ExceptionDetails errorResponse = new ExceptionDetails(ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ExceptionDetails>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ExceptionDetails errorResponse = new ExceptionDetails(
				MethodArgumentNotValidException.errorsToStringList(ex.getFieldErrors()).toString(),
				LocalDateTime.now());
		return new ResponseEntity<Object>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HoodFillerException.class)
	public final ResponseEntity<ExceptionDetails> handleHoodFillerException(Exception ex)
			throws Exception {
		ExceptionDetails errorResponse = new ExceptionDetails(ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ExceptionDetails>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
