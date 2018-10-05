package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class FuelRepository implements IFuelRepository {

    private ArrayList<Fuel> fuelTank = new ArrayList<Fuel>();

    private Optional<Fuel> findFuel(String fuelName) {
        return fuelTank.stream().filter(f -> f.getFuelName().equals(fuelName)).findFirst();
    }

    @Override
    public void addFuel(String fuelName, float price, float fuelLeft) throws EntityAlreadyExistsException {
        Optional<Fuel> search = findFuel(fuelName);
        if (search.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Fuel with name of %s already exists", fuelName));
        }
        fuelTank.add(new Fuel(fuelName, price, fuelLeft));
    }

    @Override
    public void removeFuel(String fuelName) throws EntityNotFoundException {
        Optional<Fuel> search = findFuel(fuelName);
        if (search.isPresent()) {
            fuelTank.remove(fuelTank.indexOf(search));
        } else {
            throw new EntityNotFoundException(String.format("Fuel %s can't be removed because it doesn't exist", fuelName));
        }
    }

    @Override
    public void setFuelPrice(String fuelName, float price) throws EntityNotFoundException {
        Optional<Fuel> search = findFuel(fuelName);
        if (search.isPresent()) {
            search.get().setPrice(price);
        } else {
            throw new EntityNotFoundException(String.format("Can't set fuel price, fuel %s doesn't exist"));
        }
    }
asdasdasd
    @Override
    public void setFuelLeft(String fuelName, float fuelLeft) throws EntityNotFoundException {
        Optional<Fuel> search = findFuel(fuelName);
        if (search.isPresent()) {
            search.get().setFuelLeft(fuelLeft);
        } else {
            throw new EntityNotFoundException(String.format("Can't set amount of fuel left, fuel %s doesn't exist", fuelName));
        }
    }

    @Override
    public Fuel getFuel(String fuelName) throws EntityNotFoundException {
        Optional<Fuel> search = findFuel(fuelName);
        if (search.isPresent()) {
            return search.get();
        } else {
            throw new EntityNotFoundException(String.format("Fuel %s doesn't exist", fuelName));
        }
    }

    @Override
    public ArrayList<Fuel> getFuels() {
        return fuelTank;
    }
}
