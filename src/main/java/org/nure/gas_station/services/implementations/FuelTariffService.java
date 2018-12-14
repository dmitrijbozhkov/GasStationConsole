package org.nure.gas_station.services.implementations;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.OperationException;
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
            throw new EntityNotFoundException(String.format("Fuel tariff %s doesn't exist", id));
        }
        return search.get();
    }

    @Override
    @Transactional
    public void removeFuelTariff(long id) throws OperationException {
        FuelTariff fuelTariff = getFuelTariff(id);
        if (fuelTariff.getFuel() == null) {
            fuelTariffRepository.delete(fuelTariff);
        } else {
            throw new OperationException(String.format("Can't delete fuel tariff with id %d, it's used in fuel %s", fuelTariff.getId(), fuelTariff.getFuel().getFuelName()));
        }
    }

    @Override
    @Transactional
    public void updateFuelTariff(long id, float exchangeRate) {
        FuelTariff fuelTariff = getFuelTariff(id);
        fuelTariff.setExchangeRate(exchangeRate);
        fuelTariffRepository.save(fuelTariff);
    }

    @Override
    @Transactional
    public FuelTariff createFuelTariff(float exchangeRate) {
        FuelTariff fuelTariff = new FuelTariff(exchangeRate);
        return fuelTariffRepository.save(fuelTariff);
    }
}
