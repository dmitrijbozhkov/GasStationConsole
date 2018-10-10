package org.nure.GasStation.Model.RepositoryInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.NotEnoughPrivilegesException;
import org.nure.GasStation.Model.Operation;

import java.util.ArrayList;
import java.util.Date;

public interface IOperationRepository {

    String buyFuel(String username, String fuelName, float amount) throws EntityNotFoundException;
    String fillFuel(String username, String fuelName, float amount) throws EntityNotFoundException, NotEnoughPrivilegesException;
    Operation getOperationById(String operationId) throws EntityNotFoundException;
    ArrayList<Operation> getOperations(int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getUserOperations(String username, int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getTimeOperations(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getUserTimeOperations(String username, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    /*
    ArrayList<Operation> getTimeOperations(Date before, Date after, OperationTimeFrame timeFrame, int amount, int page) throws IndexOutOfBoundsException;
    ArrayList<Operation> getUserTimeOperations(String username, Date before, Date after, OperationTimeFrame timeFrame, int amount, int page) throws IndexOutOfBoundsException;
    */
}
