package com.csiris.postservice.endpoints.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.csiris.postservice.transport.GenericErrorMessage;

@Order(value = 2)
@ControllerAdvice
public class JsonSyntaxErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String,GenericErrorMessage<String>> map = new HashMap<>();
		GenericErrorMessage<String> exceptionMessage = new GenericErrorMessage<String>(ex.getMessage(), status);
		map.put("error", exceptionMessage);
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
}
