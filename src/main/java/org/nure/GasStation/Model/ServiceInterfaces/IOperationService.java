package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IOperationService {

    String buyFuel(String username, String fuelName, float amount) throws EntityNotFoundException;
    String fillFuel(String username, String fuelName, float amount) throws EntityNotFoundException;
    Operation getOperationById(String operationId) throws EntityNotFoundException;
    List<Operation> getOperations(int amount, int page) throws IndexOutOfBoundsException;
    List<Operation> getUserOperations(String username, int amount, int page) throws IndexOutOfBoundsException;
    List<Operation> getTimeOperations(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    List<Operation> getUserTimeOperations(String username, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
}
