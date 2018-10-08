package org.nure.GasStation.Model.Interfaces;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.OperationTimeFrame;
import org.nure.GasStation.Model.Operation;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface IOperationRepository {

    void buyFuel(String username, String fuelName, float amount, float price);
    void fillFuel(String username, String fuelName, float amount);
    Operation getOperation(String operationId) throws EntityNotFoundException;
    ArrayList<Operation> getOperations(int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getUserOperations(String username, int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getTimeOperations(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getUserTimeOperations(String username, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    /*
    ArrayList<Operation> getTimeOperations(Date before, Date after, OperationTimeFrame timeFrame, int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getUserTimeOperations(String username, Date before, Date after, OperationTimeFrame timeFrame, int amount, int page) throws IndexOutOfBoundsException;
    */
}
