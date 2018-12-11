package org.nure.gas_station.exceptions;

public class InputDataValidationException extends RuntimeException {

    public InputDataValidationException() { }

    public InputDataValidationException(String message) {
        super(message);
    }
}
