package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;

import java.util.ArrayList;
import java.util.List;

public interface IFuelService {

    void addFuel(String fuelName, float price, float fuelLeft, float maxFuel, String description) throws EntityAlreadyExistsException;
    void removeFuel(String fuelName) throws EntityNotFoundException;
    Fuel getFuel(String fuelName) throws EntityNotFoundException;
    List<Fuel> getFuels();
}
