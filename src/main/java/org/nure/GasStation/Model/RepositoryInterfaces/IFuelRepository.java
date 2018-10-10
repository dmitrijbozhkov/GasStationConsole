package org.nure.GasStation.Model.RepositoryInterfaces;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;

import java.util.ArrayList;

public interface IFuelRepository {

    void addFuel(String fuelName, float price, float fuelLeft) throws EntityAlreadyExistsException;
    void removeFuel(String fuelName) throws EntityNotFoundException;
    void setFuelPrice(String fuelName, float price) throws EntityNotFoundException;
    void setFuelLeft(String fuelName, float fuelLeft) throws EntityNotFoundException;
    Fuel getFuel(String fuelName) throws EntityNotFoundException;
    ArrayList<Fuel> getFuels();
}
