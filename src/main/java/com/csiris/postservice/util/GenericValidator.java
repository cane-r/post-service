package com.csiris.postservice.util;

import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

/**
 * JSR-303 Validator Class
 */
@Component
public class GenericValidator<T> {

	private final Validator validator;

	public GenericValidator(Validator validator) {
		super();
		this.validator = validator;
	}

	public void validate(T t) {
		Objects.requireNonNull(t);
		Set<ConstraintViolation<T>> violations = validator.validate(t);

		if (!violations.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			violations.forEach(constraintViolation->
				sb./*append("Class : ").append(constraintViolation.getRootBeanClass()).append("Field : ")
						.append(constraintViolation.getPropertyPath()).append("Message : ").*/
						 append(constraintViolation.getMessage()).append(" "));
			throw new ConstraintViolationException(sb.toString(), violations);	
		}
	}
}