package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;

import java.util.Date;

public interface IOperationRepository {

    void createOperation(String username, String fuelName, float amount, float price, Date date) throws EntityAlreadyExistsException;
    void getOperation(String operationId) throws EntityNotFoundException;
    void getOperations(int amount, int page);
    void getUserOperations(String username, int amount, int page);
    void getTimeOperations(Date date, int amount, int page);
}
