package org.nure.gas_station.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import org.nure.gas_station.exceptions.*;
import org.nure.gas_station.exchange_models.exception_controller.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ExceptionResponse(ex.getClass().getCanonicalName(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseBody
    public ExceptionResponse handleEntityAlreadyExistsException(EntityNotFoundException ex, WebRequest request) {
        return new ExceptionResponse(ex.getClass().getCanonicalName(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OperationException.class)
    @ResponseBody
    public ExceptionResponse handleOperationException(OperationException ex, WebRequest request) {
        return new ExceptionResponse(ex.getClass().getCanonicalName(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ExceptionResponse(ex.getClass().getCanonicalName(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InputDataValidationException.class)
    @ResponseBody
    public ExceptionResponse handleInputDataValidationException(InputDataValidationException ex, WebRequest request) {
        return new ExceptionResponse(ex.getClass().getCanonicalName(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ExceptionResponse handleTokenExpiredException(ExpiredJwtException ex, WebRequest request) {
        return new ExceptionResponse(ex.getClass().getCanonicalName(), ex.getMessage());
    }
}
