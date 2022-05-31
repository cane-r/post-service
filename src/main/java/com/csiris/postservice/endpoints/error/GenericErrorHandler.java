package com.csiris.postservice.endpoints.error;

import org.slf4j.Logger;

public abstract class GenericErrorHandler /*extends ResponseEntityExceptionHandler */ {
	protected final Logger logger;

	public GenericErrorHandler(Logger logger) {
		super();
		this.logger = logger;
	}
}
