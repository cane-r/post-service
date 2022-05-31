package com.csiris.postservice.transport

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonValue;

open class GenericResponse <T>(
	@JsonValue private var data: T,
	private var statusCode: HttpStatus
) {

}