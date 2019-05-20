package org.nure.gas_station.services.interfaces;

import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.model.FuelTariff;

import java.util.List;


public interface IFuelTariffService {
    FuelTariff getFuelTariff(long id);
    void removeFuelTariff(long id) throws OperationException;
    void updateFuelTariff(long id, float exchangeRate);
    FuelTariff createFuelTariff(float exchangeRate);
    List<FuelTariff> getTariffs();
}
