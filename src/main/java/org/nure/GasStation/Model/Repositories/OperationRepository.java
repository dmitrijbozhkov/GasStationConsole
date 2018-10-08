package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.OperationTimeFrame;
import org.nure.GasStation.Model.Enumerations.OperationTypes;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.Interfaces.IFuelRepository;
import org.nure.GasStation.Model.Interfaces.IOperationRepository;
import org.nure.GasStation.Model.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class OperationRepository implements IOperationRepository {

    @Autowired
    private CommonsRepository commmons;
    @Autowired
    private IFuelRepository fuelRepository;

    private ArrayList<Operation> operationList = new ArrayList<Operation>();

    private Optional<Operation> findOperation(String operationId) {
        return operationList.stream().filter(f -> f.getOperationId().equals(operationId)).findFirst();
    }

    private String generateOperationId() {
        String operationId = "";
        while (true) {
            operationId = commmons.generateStringId(7);
            if (!findOperation(operationId).isPresent()) {
                return operationId;
            }
        }
    }

    private int makePage(int amount, int page) throws IndexOutOfBoundsException {
        if (page - 1 < 0) {
            throw new IndexOutOfBoundsException("Page should start from 1");
        }
        if (amount < 1) {
            throw new IndexOutOfBoundsException("Can't take less than 1 item");
        }
        return amount * (page - 1);
    }

    @Override
    public void buyFuel(String username, String fuelName, float amount, float price) {
        String operationId = generateOperationId();
        Fuel currentFuel = fuelRepository.getFuel(fuelName);
        currentFuel.setFuelLeft(currentFuel.getFuelLeft() - amount);
        operationList.add(new Operation(
                operationId,
                amount,
                price,
                new Date(),
                fuelName,
                username,
                OperationTypes.OPERATION_BUY));
    }

    @Override
    public void fillFuel(String username, String fuelName, float amount) {
        String operationId = generateOperationId();
        Fuel currentFuel = fuelRepository.getFuel(fuelName);
        currentFuel.setFuelLeft(currentFuel.getFuelLeft() + amount);
        operationList.add(new Operation(
                operationId,
                amount,
                currentFuel.getPrice(),
                new Date(),
                fuelName,
                username,
                OperationTypes.OPERATION_BUY));
    }

    @Override
    public Operation getOperation(String operationId) throws EntityNotFoundException {
        Optional<Operation> search = findOperation(operationId);
        if (search.isPresent()){
            return search.get();
        }
        throw new EntityNotFoundException(String.format("Couldn't find operation by id %s", operationId));
    }

    /*
    * Pages from 1
     */
    @Override
    public ArrayList<Operation> getOperations(int amount, int page) throws IndexOutOfBoundsException {
        page = makePage(amount, page);
        return operationList
                .stream()
                .sorted(Comparator.comparing(op -> op.getDate()))
                .skip(page)
                .limit(amount)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Operation> getUserOperations(String username, int amount, int page) throws IndexOutOfBoundsException {
        page = makePage(amount, page);
        return operationList
                .stream()
                .filter(op -> op.getUsername().equals(username))
                .sorted(Comparator.comparing(op -> op.getDate()))
                .skip(page)
                .limit(amount)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Operation> getTimeOperations(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException {
        page = makePage(amount, page);
        return operationList
                .stream()
                .filter(op -> op.getDate().after(before) && op.getDate().before(after))
                .sorted(Comparator.comparing(op -> op.getDate()))
                .skip(page)
                .limit(amount)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Operation> getUserTimeOperations(String username, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException {
        page = makePage(amount, page);
        return operationList
                .stream()
                .filter(op -> op.getDate().after(before) && op.getDate().before(after) && op.getUsername().equals(username))
                .sorted(Comparator.comparing(op -> op.getDate()))
                .skip(page)
                .limit(amount)
                .collect(Collectors.toCollection(ArrayList::new));
    }


//    @Override
//    public ArrayList<Operation> getTimeOperations(Date before, Date after, OperationTimeFrame timeFrame, int amount, int page) {
//        ChronoUnit units;
//        switch (timeFrame){
//            case YEARS:
//                units = ChronoUnit.YEARS;
//                break;
//            case DAYS:
//                units = ChronoUnit.DAYS;
//                break;
//            default:
//                units = ChronoUnit.MONTHS;
//        }
//        long range = units.between(ZonedDateTime.ofInstant(before.toInstant(), ZoneId.systemDefault()),
//                ZonedDateTime.ofInstant(after.toInstant(), ZoneId.systemDefault()));
//
//    }
//
//    @Override
//    public ArrayList<Operation> getUserTimeOperations(String username, Date before, Date after, OperationTimeFrame timeFrame, int amount, int page) throws IndexOutOfBoundsException {
//        return null;
//    }
}
