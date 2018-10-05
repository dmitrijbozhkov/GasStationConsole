package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class OperationRepository implements IOperationRepository {

    @Autowired
    private CommonsRepository commmons;

    @Override
    public void createOperation(String username, String fuelName, float amount, float price, Date date) throws EntityAlreadyExistsException {

    }

    @Override
    public void getOperation(String operationId) throws EntityNotFoundException {

    }

    @Override
    public void getOperations(int amount, int page) {

    }

    @Override
    public void getUserOperations(String username, int amount, int page) {

    }

    @Override
    public void getTimeOperations(Date date, int amount, int page) {

    }
}
