package org.nure.gas_station.services.interfaces;

import org.nure.gas_station.model.FuelTariff;


public interface IFuelTariffService {
    FuelTariff getFuelTariff(long id);
    void removeFuelTariff(long id);
    void updateFuelTariff(long id, float exchangeRate, String fuelName);
    FuelTariff createFuelTariff(float exchangeRate);
}
