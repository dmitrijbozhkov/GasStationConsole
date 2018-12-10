package org.nure.gas_station.services.implementations;

import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelStorage;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.repositories.interfaces.IFuelRepository;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.nure.gas_station.services.interfaces.IFuelTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FuelService implements IFuelService {

    @Autowired
    private IFuelRepository fuelRepository;
    @Autowired
    private IFuelTariffService fuelTariffService;

    @Override
    @Transactional
    public void addFuel(String fuelName, long tariffId, float fuelLeft) throws EntityAlreadyExistsException {
        try {
            getFuel(fuelName);
        } catch (EntityNotFoundException ex) {
            fuelRepository.save(new Fuel(fuelName, new FuelStorage(fuelLeft), fuelTariffService.getFuelTariff(tariffId)));
            return;
        }
        throw new EntityAlreadyExistsException(String.format("Fuel with name of %s already exists", fuelName));
    }

    @Override
    @Transactional
    public void removeFuel(String fuelName) throws EntityNotFoundException {
        fuelRepository.delete(getFuel(fuelName));
    }

    @Override
    public Fuel getFuel(String fuelName) throws EntityNotFoundException {
        Optional<Fuel> search = fuelRepository.findById(fuelName);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("Fuel %s doesn't exist", fuelName));
        }
        return search.get();
    }

    @Override
    @Transactional
    public void updateFuelLeft(String fuelName, float fuelLeft) throws EntityNotFoundException {
        Fuel fuel = getFuel(fuelName);
        fuel.getFuelStorage().setFuelAmount(fuelLeft);
    }

    @Override
    @Transactional
    public void updateFuelTariff(String fuelName, long tariffId) throws EntityNotFoundException {
        FuelTariff tariff = fuelTariffService.getFuelTariff(tariffId);
        Fuel fuel = getFuel(fuelName);
        fuel.setFuelTariff(tariff);
    }

    @Override
    @Transactional
    public void updateFuelName(String fuelName, String nextFuelName) throws EntityNotFoundException {
        Fuel fuel = getFuel(fuelName);
        fuel.setFuelName(nextFuelName);
    }

    @Override
    public List<Fuel> getFuels() {
        return fuelRepository.findAll();
    }
}
