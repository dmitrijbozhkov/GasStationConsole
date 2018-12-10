package org.nure.gas_station.services.interfaces;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.enumerations.OrderType;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface IFuelOrderService {
    void orderFuel(String username, String fuelName, float amount, OrderType orderType, Date orderDate) throws EntityNotFoundException, OperationException;
    FuelOrder getOrderById(long orderId) throws EntityNotFoundException;
    Page<FuelOrder> getOrders(int amount, int page) throws IndexOutOfBoundsException;
    Page<FuelOrder> getUserOrders(String username, int amount, int page) throws IndexOutOfBoundsException;
    Page<FuelOrder> getTimeOrders(Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
    Page<FuelOrder> getFuelTimeOrders(String fuelName, Date before, Date after, int amount, int page) throws IndexOutOfBoundsException;
}
