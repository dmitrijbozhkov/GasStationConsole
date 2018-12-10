package org.nure.GasStation.Exceptions;

/**
 * Exception, that will be thrown if record not found in storage
 */
public class EntityNotFoundException extends IllegalArgumentException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
