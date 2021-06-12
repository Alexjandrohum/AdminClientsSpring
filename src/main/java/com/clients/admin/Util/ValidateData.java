package com.clients.admin.Util;

import com.clients.admin.exception.ApiException;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ValidateData<T> {

    public void validateObject(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        consumeViolations(violations);
    }

    public static void consumeViolations(Set<? extends ConstraintViolation<?>> violations) {
        StringBuffer sb = new StringBuffer();
        for(ConstraintViolation<?> violation : violations) {
            sb.append("El parametro "+"'"+violation.getPropertyPath().toString() + "'");
            sb.append(violation.getMessage());
            sb.append(", ");
        }
        if (sb.length() > 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), new ArrayList(Arrays.asList(sb)));
        }
    }
}
