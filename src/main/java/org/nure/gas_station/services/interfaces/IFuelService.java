package org.nure.gas_station.services.interfaces;

import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.Fuel;

import java.util.List;

public interface IFuelService {

    void addFuel(String fuelName, long tariffId, float fuelLeft) throws EntityAlreadyExistsException;
    void removeFuel(String fuelName) throws EntityNotFoundException;
    Fuel getFuel(String fuelName) throws EntityNotFoundException;
    void updateFuelLeft(String fuelName, float fuelLeft) throws EntityNotFoundException;
    void updateFuelTariff(String fuelName, long tariffId) throws EntityNotFoundException;
    void updateFuelName(String fuelName, String nextFuelName) throws EntityNotFoundException;
    List<Fuel> getFuels();
}
