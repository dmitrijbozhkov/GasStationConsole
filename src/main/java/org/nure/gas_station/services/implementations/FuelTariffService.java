package org.nure.gas_station.services.implementations;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.repositories.interfaces.IFuelTariffRepository;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.nure.gas_station.services.interfaces.IFuelTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FuelTariffService implements IFuelTariffService {

    @Autowired
    private IFuelTariffRepository fuelTariffRepository;

    @Autowired
    private IFuelService fuelService;


    @Override
    public FuelTariff getFuelTariff(long id) throws EntityNotFoundException {
        Optional<FuelTariff> search = fuelTariffRepository.findById(id);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("Fuel %s doesn't exist", id));
        }
        return search.get();
    }

    @Override
    @Transactional
    public void removeFuelTariff(long id) {
        fuelTariffRepository.delete(getFuelTariff(id));
    }

    @Override
    @Transactional
    public void updateFuelTariff(long id, float exchangeRate, String fuelName) {
        FuelTariff fuelTariff = getFuelTariff(id);
        fuelTariff.setExchangeRate(exchangeRate);
        if (fuelName != null && !fuelName.isEmpty()) {
            Fuel fuel = fuelService.getFuel(fuelName);
            fuelTariff.setFuel(fuel);
        } else {
            fuelTariff.setFuel(null);
        }
        fuelTariffRepository.save(fuelTariff);
    }

    @Override
    @Transactional
    public FuelTariff createFuelTariff(float exchangeRate) {
        FuelTariff fuelTariff = new FuelTariff(exchangeRate);
        return fuelTariffRepository.save(fuelTariff);
    }
}
