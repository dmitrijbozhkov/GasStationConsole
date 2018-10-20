package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.OperationException;
import org.nure.GasStation.Model.Operation;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IOperationService {

    void buyFuel(String username, String fuelName, float amount) throws EntityNotFoundException, OperationException;
    void fillFuel(String username, String fuelName, float amount) throws EntityNotFoundException;
    Operation getOperationById(long operationId) throws EntityNotFoundException;
    Page<Operation> getOperations(int amount, int page) throws IndexOutOfBoundsException;
    Page<Operation> getUserOperations(String username, int amount, int page) throws IndexOutOfBoundsException;
    Page<Operation> getTimeOperations(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    Page<Operation> getUserTimeOperations(String username, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
}
