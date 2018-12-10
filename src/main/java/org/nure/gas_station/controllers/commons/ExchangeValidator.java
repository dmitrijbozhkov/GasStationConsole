package org.nure.gas_station.controllers.commons;

import org.nure.gas_station.exceptions.InputDataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ExchangeValidator {
    @Autowired
    private Validator validator;

    public void validateConstrains(Object exchangeModel) {
        Set<ConstraintViolation<Object>> validationErrors = validator.validate(exchangeModel);
        if (validationErrors.size() > 0) {
            throw new InputDataValidationException(validationErrors.iterator().next().getMessage());
        }
    }
}