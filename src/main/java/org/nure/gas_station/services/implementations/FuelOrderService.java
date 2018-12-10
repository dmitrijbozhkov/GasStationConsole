package org.nure.gas_station.services.implementations;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelVolumeOfSales;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelsVolumeOfSales;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.FuelStorage;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuelOrderService implements IFuelOrderService {

    @Autowired
    private IFuelService fuelService;
    @Autowired
    private IFuelOrderRepository fuelOrderRepository;
    @Autowired
    private IAdminService adminService;

    private Pageable getPage(int page, int amount) {
        return PageRequest.of(page, amount, Sort.Direction.DESC, "orderDate");
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

    private void removeFuelFromStorage(FuelStorage storage, float fuelAmount) {
        float currentFuelAmount = storage.getFuelAmount();
        storage.setFuelAmount(currentFuelAmount - fuelAmount);
    }

    private FuelOrder orderFuelByCurrency(Fuel fuel, GasStationUser user, float moneyAmount, Date orderDate) throws OperationException {
        float fuelAmount =  moneyAmount / fuel.getFuelTariff().getExchangeRate();
        checkOrderValid(fuel, orderDate, fuelAmount);
        removeFuelFromStorage(fuel.getFuelStorage(), fuelAmount);
        return new FuelOrder(moneyAmount, OrderType.FUEL_BY_CURRENCY, orderDate, fuel, fuel.getFuelTariff(), user);
    }

    private FuelOrder orderCurrencyByFuel(Fuel fuel, GasStationUser user, float fuelAmount, Date orderDate) throws OperationException {
        checkOrderValid(fuel, orderDate, fuelAmount);
        removeFuelFromStorage(fuel.getFuelStorage(), fuelAmount);
        return new FuelOrder(fuelAmount, OrderType.CURRENCY_BY_FUEL, orderDate, fuel, fuel.getFuelTariff(), user);
    }

    private List<FuelOrder> getFuelTimeOrders(Fuel fuel, Date before, Date after) {
        return fuelOrderRepository.findAllByFuelAndOrderDateBetween(fuel, before, after);
    }

    private FuelsVolumeOfSales countFuelsVolumeOfSales(List<Fuel> fuels, Date before, Date after) {
        List<FuelVolumeOfSales> fuelVolumeOfSales = fuels
                .stream()
                .map(f -> {
                    return new FuelVolumeOfSales(f, getFuelTimeOrders(f, before, after));
                })
                .collect(Collectors.toList());
        return new FuelsVolumeOfSales(fuelVolumeOfSales);
    }

    @Override
    @Transactional
    public void orderFuel(String username, String fuelName, float amount, OrderType orderType, Date orderDate) throws EntityNotFoundException, OperationException {
        Fuel currentFuel = fuelService.getFuel(fuelName);
        GasStationUser currentUser = adminService.getUser(username);
        FuelOrder currentOrder;
        switch (orderType) {
            case CURRENCY_BY_FUEL:
                currentOrder = orderCurrencyByFuel(currentFuel, currentUser, amount, orderDate);
                break;
            case FUEL_BY_CURRENCY:
                currentOrder = orderFuelByCurrency(currentFuel, currentUser, amount, orderDate);
                break;
                default:
                    throw new OperationException("Please provide correct order type");
        }
        fuelOrderRepository.save(currentOrder);
    }

    @Override
    public FuelOrder getOrderById(long orderId) throws EntityNotFoundException {
        Optional<FuelOrder> search = fuelOrderRepository.findById(orderId);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("Operation by id %s doesn't exist", orderId));
        }
        return search.get();
    }

    @Override
    @Transactional
    public void removeFuelOrder(long orderId) throws EntityNotFoundException {
        fuelOrderRepository.delete(getOrderById(orderId));
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

    @Override
    public Page<FuelOrder> getFuelOrders(String fuelName, int amount, int page) throws IndexOutOfBoundsException {
        Fuel fuel = fuelService.getFuel(fuelName);
        return fuelOrderRepository.findAllByFuel(fuel, getPage(page, amount));
    }

    @Override
    public FuelsVolumeOfSales getVolumeOfSalesBetweenDates(Date before, Date after) {
        List<Fuel> fuels = fuelService.getFuels();
        return countFuelsVolumeOfSales(fuels, before, after);
    }

    @Override
    public FuelsVolumeOfSales getFuelsVolumeOfSalesBetweenDates(List<String> fuelNames, Date before, Date after) {
        List<Fuel> fuels = fuelNames
                .stream()
                .map(n -> fuelService.getFuel(n))
                .collect(Collectors.toList());
        return countFuelsVolumeOfSales(fuels, before, after);
    }
}
