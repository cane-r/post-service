package com.csiris.postservice.endpoints.error;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.csiris.postservice.transport.GenericErrorMessage;

@ControllerAdvice
//Handler for Bean Validation Errors
public class ConstraintViolationExceptionErrorHandler extends GenericErrorHandler {

	public ConstraintViolationExceptionErrorHandler(Logger logger) {
		super(logger);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Map<GenericErrorMessage<String>, GenericErrorMessage<String>>> handleConstraintViolationException(
			ConstraintViolationException ex, WebRequest request) {
		logger.error(ex.toString());
		Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
		Map<GenericErrorMessage<String>, GenericErrorMessage<String>> errorMap = new HashMap<>();
		set.forEach(e -> errorMap.put(new GenericErrorMessage<String>(e.getPropertyPath().toString(), null),
				new GenericErrorMessage<String>(e.getMessage(), null)));
		return new ResponseEntity<Map<GenericErrorMessage<String>, GenericErrorMessage<String>>>(errorMap,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
