package com.csiris.postservice.endpoints.error;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;

@ControllerAdvice
public class GeneralErrorHandler extends GenericErrorHandler {
	

	public GeneralErrorHandler(Logger logger) {
		super(logger);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	protected ResponseEntity<String> handleRuntimeExceptions(RuntimeException ex, WebRequest request) {
		logger.error(ex.toString());
		//GenericResponse<String> exception = new GenericResponse<String>(ex.getMessage(), null);
		return new ResponseEntity<String>("General RuntimeException error handler in action.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { InvalidDefinitionException.class })
	protected ResponseEntity<String> handleRInvalidDefinitionException(InvalidDefinitionException ex, WebRequest request) {
		logger.error(ex.toString());
		//GenericResponse<String> exception = new GenericResponse<String>(ex.getMessage(), null);
		return new ResponseEntity<String>("General InvalidDefinitionException error handler in action.", HttpStatus.BAD_REQUEST);
	}
}