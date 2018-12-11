package org.nure.gas_station.exceptions;

import java.rmi.AccessException;

public class NotEnoughPrivilegesException extends AccessException {

    public NotEnoughPrivilegesException(String s) {
        super(s);
    }
}
