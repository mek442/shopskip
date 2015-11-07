package com.skip.service;



import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.skip.rest.exception.ValidationException;

import java.util.Set;

public abstract class BaseService {

    private Validator validator;

    public BaseService(Validator validator) {
        this.validator = validator;
    }

    protected void validate(Object request) {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ValidationException(constraintViolations);
        }
    }

}
