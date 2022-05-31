package com.csiris.postservice.endpoints.error;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(value = 1)
public class MethodArgumentExceptionErrorHandler extends GenericErrorHandler {

	public MethodArgumentExceptionErrorHandler(Logger logger) {
		super(logger);
		//logger.info("LoggerControllerAdvice : ? " + this.getClass().isAnnotationPresent(LoggerControllerAdvice.class));
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<Map<String, String>> handleMethodArgumentExceptionErrorHandler(MethodArgumentNotValidException ex,
			WebRequest request) {
		logger.error(ex.toString());
		//ex.getBindingResult().getAllErrors().forEach(e->logger.error( e.getDefaultMessage()));
		//ex.getBindingResult().getFieldErrors().forEach(e->logger.error( e.getField() + " : " + e.getDefaultMessage()));
		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(e->map.put(e.getField(), e.getDefaultMessage()));
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
	}
}
