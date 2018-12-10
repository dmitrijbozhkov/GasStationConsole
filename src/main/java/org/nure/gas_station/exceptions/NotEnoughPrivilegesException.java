package org.nure.GasStation.Exceptions;

import java.rmi.AccessException;

public class NotEnoughPrivilegesException extends AccessException {

    public NotEnoughPrivilegesException(String s) {
        super(s);
    }
}
