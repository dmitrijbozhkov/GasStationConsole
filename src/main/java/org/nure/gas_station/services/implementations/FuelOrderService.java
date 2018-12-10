package org.nure.gas_station.services.implementations;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.services.interfaces.IAdminService;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.nure.gas_station.services.interfaces.IFuelOrderService;
import org.nure.gas_station.model.enumerations.OrderType;
import org.nure.gas_station.repositories.interfaces.IFuelOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class FuelOrderServiceService implements IFuelOrderService {

    @Autowired
    private IFuelService fuelService;
    @Autowired
    private IFuelOrderRepository fuelOrderRepository;
    @Autowired
    private IAdminService adminService;

    private Pageable getPage(int page, int amount) {
        return PageRequest.of(page, amount, Sort.Direction.DESC, "date");
    }

    private void checkOrderValid(Fuel fuel, Date orderDate, float fuelAmount) throws OperationException {
        if (FuelOrder.getMaxOrderVolume() < fuelAmount || fuelAmount < 0) {
            throw new OperationException(String.format("Can't order more than %d or less than 0 liters of fuel", FuelOrder.getMaxOrderVolume()));
        }
        if (fuel.getFuelStorage().getFuelAmount() < fuelAmount) {
            throw new OperationException("Not enough fuel in the storage");
        }
        if (orderDate.before(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))) {
            throw new OperationException("Order can be served no sooner than tomorrow");
        }
    }

    private FuelOrder orderFuelByCurrency(Fuel fuel, GasStationUser user, float moneyAmount, Date orderDate) throws OperationException {
        float fuelAmount = fuel.getFuelTariff().getExchangeRate() * moneyAmount;
        checkOrderValid(fuel, orderDate, fuelAmount);
        float currentFuelAmount = fuel.getFuelStorage().getFuelAmount();
        fuel.getFuelStorage().setFuelAmount(currentFuelAmount - fuelAmount);
        return new FuelOrder(fuelAmount, OrderType.FUEL_WORTH_OF_СCURRENCY, orderDate, fuel, fuel.getFuelTariff(), user);
    }

    private FuelOrder orderCurrencyWorthOfFuel(Fuel fuel, GasStationUser user, float fuelAmount, Date orderDate) throws OperationException {
        checkOrderValid(fuel, orderDate, fuelAmount);
        float currentFuelAmount = fuel.getFuelStorage().getFuelAmount();
        fuel.getFuelStorage().setFuelAmount(currentFuelAmount - fuelAmount);
        return new FuelOrder(fuelAmount, OrderType.FUEL_WORTH_OF_СCURRENCY, orderDate, fuel, fuel.getFuelTariff(), user);
    }

    @Override
    @Transactional
    public void orderFuel(String username, String fuelName, float amount, OrderType orderType, Date orderDate) throws EntityNotFoundException, OperationException {
        Fuel currentFuel = fuelService.getFuel(fuelName);
        GasStationUser currentUser = adminService.getUser(username);
        FuelOrder currentOrder;
        switch (orderType) {
            case CURRENCY_WORTH_OF_FUEL:
                currentOrder = orderCurrencyWorthOfFuel(currentFuel, currentUser, amount, orderDate);
                break;
            case FUEL_WORTH_OF_СCURRENCY:
                currentOrder = orderFuelByCurrency(currentFuel, currentUser, amount, orderDate);
                break;
                default:
                    throw new OperationException("Please provide correct order type");
        }
        fuelOrderRepository.save(currentOrder);
    }

    @Override
    public FuelOrder getOrderById(long operationId) throws EntityNotFoundException {
        Optional<FuelOrder> search = fuelOrderRepository.findById(operationId);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("Operation by id %s doesn't exist", operationId));
        }
        return search.get();
    }

    @Override
    public Page<FuelOrder> getOrders(int amount, int page) throws IndexOutOfBoundsException {
        return fuelOrderRepository.findAll(getPage(page, amount));
    }

    @Override
    public Page<FuelOrder> getUserOrders(String username, int amount, int page) throws IndexOutOfBoundsException {
        GasStationUser user = adminService.getUser(username);
        return fuelOrderRepository.findAllByGasStationUser(user, getPage(page, amount));
    }

    @Override
    public Page<FuelOrder> getTimeOrders(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException {
        return fuelOrderRepository.findAllByOrderDateBetween(before, after, getPage(page, amount));
    }

    @Override
    public Page<FuelOrder> getFuelTimeOrders(String fuelName, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException {
        Fuel fuel = fuelService.getFuel(fuelName);
        return fuelOrderRepository.findAllByFuelAndOrderDateBetween(fuel, before, after, getPage(page, amount));
    }
}
