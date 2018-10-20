package org.nure.GasStation.Model.Services;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.OperationException;
import org.nure.GasStation.Model.Enumerations.OperationTypes;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.Operation;
import org.nure.GasStation.Model.RepositoryInterfaces.IOperationRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IAdminService;
import org.nure.GasStation.Model.ServiceInterfaces.IFuelService;
import org.nure.GasStation.Model.ServiceInterfaces.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OperationService implements IOperationService {

    @Autowired
    private IFuelService fuelService;
    @Autowired
    private IOperationRepository operationRepository;
    @Autowired
    private IAdminService adminService;

    private Pageable getPage(int page, int amount) {
        return new PageRequest(page, amount, Sort.Direction.DESC, "date");
    }

    @Override
    public void buyFuel(String username, String fuelName, float amount) throws EntityNotFoundException, OperationException {
        Fuel currentFuel = fuelService.getFuel(fuelName);
        GasStationUser currentUser = adminService.getUser(username);
        if (currentFuel.getFuelLeft() < amount) {
            throw new OperationException("Not enough fuel");
        }
        currentFuel.setFuelLeft(currentFuel.getFuelLeft() - amount);
        operationRepository.save(new Operation(
                amount,
                currentFuel.getPrice(),
                new Date(),
                currentFuel,
                currentUser,
                OperationTypes.OPERATION_BUY));
    }

    @Override
    public void fillFuel(String username, String fuelName, float amount) throws EntityNotFoundException {
        Fuel currentFuel = fuelService.getFuel(fuelName);
        GasStationUser currentUser = adminService.getUser(username);
        currentFuel.setFuelLeft(currentFuel.getFuelLeft() + amount);
        operationRepository.save(new Operation(
                amount,
                currentFuel.getPrice(),
                new Date(),
                currentFuel,
                currentUser,
                OperationTypes.OPERATION_FILL));
    }

    @Override
    public Operation getOperationById(long operationId) throws EntityNotFoundException {
        Optional<Operation> search = operationRepository.findById(operationId);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("Operation by id %s doesn't exist", operationId));
        }
        return search.get();
    }

    @Override
    public Page<Operation> getOperations(int amount, int page) throws IndexOutOfBoundsException {
        return operationRepository.findAll(getPage(page, amount));
    }

    @Override
    public Page<Operation> getUserOperations(String username, int amount, int page) throws IndexOutOfBoundsException {
        GasStationUser user = adminService.getUser(username);
        return operationRepository.findAllByGasStationUser(user, getPage(page, amount));
    }

    @Override
    public Page<Operation> getTimeOperations(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException {
        return operationRepository.findAllByOperationDateBetween(before, after, getPage(page, amount));
    }

    @Override
    public Page<Operation> getUserTimeOperations(String username, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException {
        GasStationUser user = adminService.getUser(username);
        return operationRepository.findAllByGasStationUserAndOperationDateBetween(user, before, after, getPage(page, amount));
    }
}
