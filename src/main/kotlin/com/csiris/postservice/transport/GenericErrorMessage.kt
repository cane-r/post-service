package com.csiris.postservice.transport

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonValue;

class GenericErrorMessage<T>(
	@JsonValue var data: T,
	var statusCode: HttpStatus
) : GenericResponse<T>(data, statusCode) {}


/*
 data class GenericErrorMessage<T>(
	@JsonValue private var data: T,
	private var statusCode: HttpStatus
) {

}
 
 */
		