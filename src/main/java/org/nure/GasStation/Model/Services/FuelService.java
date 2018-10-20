package org.nure.GasStation.Model.Services;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.RepositoryInterfaces.IFuelRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelService implements IFuelService {

    @Autowired
    private IFuelRepository fuelRepository;

    @Override
    public void addFuel(String fuelName, float price, float fuelLeft) throws EntityAlreadyExistsException {
        Optional<Fuel> search = fuelRepository.findById(fuelName);
        if (search.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Fuel with name of %s already exists", fuelName));
        }
        fuelRepository.save(new Fuel(fuelName, price, fuelLeft));
    }

    @Override
    public void removeFuel(String fuelName) throws EntityNotFoundException {
        Optional<Fuel> search = fuelRepository.findById(fuelName);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("Can't delete, fuel %s doesn't exist", fuelName));
        }
        fuelRepository.delete(search.get());
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
    public List<Fuel> getFuels() {
        return fuelRepository.findAll();
    }
}
