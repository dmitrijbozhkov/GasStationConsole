package org.nure.gas_station.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.model.*;
import org.nure.gas_station.model.enumerations.OrderType;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.repositories.interfaces.IFuelOrderRepository;
import org.nure.gas_station.services.interfaces.IAdminService;
import org.nure.gas_station.services.interfaces.IFuelOrderService;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuelOrderServiceTest {

    @Autowired
    private IFuelOrderService fuelOrderService;

    @MockBean
    private IAdminService adminService;
    @MockBean
    private IFuelService fuelService;
    @MockBean
    private IFuelOrderRepository fuelOrderRepository;

    // Default fuel
    private final String fuelName = "95";
    private final long tariffId = 15;
    private final float exchangeRate = (float) 9.99;
    private final long fuelStorageId = 99;
    private final float fuelLeft = 5000;
    private Fuel fuel;
    // Default user
    private final String username = "matviei";
    private final String password = "pass1234";
    private final String name = "pepe";
    private final String surname = "keke";
    private final UserRoles userRole = UserRoles.ROLE_ADMIN;
    private GasStationUser gasStationUser;
    // Default order
    private final long fuelOrderId = 33;
    private final float orderAmount = FuelOrder.getMaxOrderVolume() - 50;
    private final OrderType orderType = OrderType.CURRENCY_BY_FUEL;
    private final Date orderDate = Date.from(Instant.now().plus(3, ChronoUnit.DAYS));
    private FuelOrder fuelOrder;

    @Before
    public void setUpOrderData() {
        FuelStorage storage = new FuelStorage(fuelStorageId, fuelLeft);
        FuelTariff fuelTariff = new FuelTariff(tariffId, exchangeRate);
        this.fuel = new Fuel(fuelName, storage, fuelTariff);
        this.gasStationUser = new GasStationUser(username, password, name, surname, userRole);
        this.fuelOrder = new FuelOrder(fuelOrderId, orderAmount, orderType, orderDate, this.fuel, fuelTariff, this.gasStationUser);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfFuelByCurrencyOrderFuelAmountIsBiggerThanMaxOrderVolume() throws Exception {
        float fuelAmount = FuelOrder.getMaxOrderVolume() + 200;
        float moneyAmount = fuelAmount * exchangeRate;
        OrderType orderFuelType = OrderType.FUEL_BY_CURRENCY;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, moneyAmount, orderFuelType, orderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfCurrencyByFuelOrderFuelAmountIsBiggerThanMaxOrderVolume() throws Exception {
        float fuelAmount = FuelOrder.getMaxOrderVolume() + 200;
        OrderType orderCurrencyType = OrderType.CURRENCY_BY_FUEL;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, fuelAmount, orderCurrencyType, orderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfFuelByCurrencyOrderFuelAmountIsLessThan0() throws Exception {
        float fuelAmount = -3;
        float moneyAmount = fuelAmount * exchangeRate;
        OrderType orderFuelType = OrderType.FUEL_BY_CURRENCY;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, moneyAmount, orderFuelType, orderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfCurrencyByFuelOrderFuelAmountIsLessThan0() throws Exception {
        float fuelAmount = -6;
        OrderType orderCurrencyType = OrderType.CURRENCY_BY_FUEL;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, fuelAmount, orderCurrencyType, orderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfFuelByCurrencyOrderFuelAmountIsBiggerThanFuelLeft() throws Exception {
        float fuelAmount = FuelOrder.getMaxOrderVolume() - 1;
        float moneyAmount = fuelAmount * exchangeRate;
        OrderType orderFuelType = OrderType.FUEL_BY_CURRENCY;
        fuel.getFuelStorage().setFuelAmount(fuelAmount - 1);
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, moneyAmount, orderFuelType, orderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfCurrencyByFuelOrderFuelAmountIsBiggerThanFuelLeft() throws Exception {
        float fuelAmount = FuelOrder.getMaxOrderVolume() - 1;
        fuel.getFuelStorage().setFuelAmount(fuelAmount - 1);
        OrderType orderCurrencyType = OrderType.CURRENCY_BY_FUEL;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, fuelAmount, orderCurrencyType, orderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfFuelByCurrencyOrderFuelAmountHasDateLessThanNowPlusOneDay() throws Exception {
        float fuelAmount = FuelOrder.getMaxOrderVolume() - 1;
        float moneyAmount = fuelAmount * exchangeRate;
        Date wrongOrderDate = Date.from(Instant.now());
        OrderType orderFuelType = OrderType.FUEL_BY_CURRENCY;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, moneyAmount, orderFuelType, wrongOrderDate);
    }

    @Test(expected = OperationException.class)
    public void testOrderFuelShouldThrowOperationExceptionIfCurrencyByFuelOrderFuelAmountHasDateLessThanNowPlusOneDay() throws Exception {
        float fuelAmount = FuelOrder.getMaxOrderVolume() - 1;
        Date wrongOrderDate = Date.from(Instant.now());
        OrderType orderCurrencyType = OrderType.CURRENCY_BY_FUEL;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, fuelAmount, orderCurrencyType, wrongOrderDate);
    }

    @Test
    public void testOrderFuelShouldRemoveFuelByCurrencyIfFuelByCurrencyOrderTypeGiven() throws Exception {
        float moneyAmount = orderAmount * exchangeRate;
        OrderType orderFuelType = OrderType.FUEL_BY_CURRENCY;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, moneyAmount, orderFuelType, orderDate);
        assertEquals(fuel.getFuelStorage().getFuelAmount() + (moneyAmount / exchangeRate), fuelLeft, 0);
    }

    @Test
    public void testOrderFuelShouldRemoveCurrencyByFuelIfCurrencyByFuelOrderTypeGiven() throws Exception {
        float fuelAmount = orderAmount;
        OrderType orderCurrencyType = OrderType.CURRENCY_BY_FUEL;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, fuelAmount, orderCurrencyType, orderDate);
        assertEquals(fuel.getFuelStorage().getFuelAmount() + fuelAmount, fuelLeft, 0);
    }

    @Test
    public void testOrderFuelShouldSaveValidOrderIfFuelByCurrencyOrderTypeGiven() throws Exception {
        float moneyAmount = orderAmount * exchangeRate;
        OrderType orderFuelType = OrderType.FUEL_BY_CURRENCY;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, moneyAmount, orderFuelType, orderDate);
        verify(fuelOrderRepository).save(argThat(o -> {
            return o.getFuel().equals(fuel) && o.getGasStationUser().equals(gasStationUser) && o.getAmount() == moneyAmount && o.getOrderType().equals(orderFuelType);
        }));
    }

    @Test
    public void testOrderFuelShouldSaveValidOrderIfCurrencyByFuelOrderTypeGiven() throws Exception {
        float fuelAmount = orderAmount;
        OrderType orderCurrencyType = OrderType.CURRENCY_BY_FUEL;
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.orderFuel(username, fuelName, fuelAmount, orderCurrencyType, orderDate);
        verify(fuelOrderRepository).save(argThat(o -> {
            return o.getFuel().equals(fuel) && o.getGasStationUser().equals(gasStationUser) && o.getAmount() == fuelAmount && o.getOrderType().equals(orderCurrencyType);
        }));
    }

    @Test
    public void testGetOrderByIdShouldReturnFuelOrderByGivenId() {
        given(fuelOrderRepository.findById(fuelOrderId)).willReturn(Optional.of(fuelOrder));
        FuelOrder operationFinding = fuelOrderService.getOrderById(fuelOrderId);
        assertEquals(fuelOrder, operationFinding);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetOperationByIdShouldThrowEntityNotFoundExceptionIfOperationNotFound() {
        given(fuelOrderRepository.findById(fuelOrderId)).willReturn(Optional.empty());
        fuelOrderService.getOrderById(fuelOrderId);
    }

    @Test
    public void testGetOrdersShouldReturnPageableOfFuelOperationsInDescendingOrderByDate() {
        int page = 0;
        int amount = 5;
        fuelOrderService.getOrders(amount, page);
        verify(fuelOrderRepository).findAll((PageRequest) argThat(req -> {
            PageRequest rightRequest = (PageRequest) req;
            return rightRequest.getPageNumber() == page && rightRequest.getPageSize() == amount && rightRequest.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testGetUserOrdersShouldReturnPageableOfUserFuelOrders() {
        int page = 1;
        int amount = 5;
        given(adminService.getUser(username)).willReturn(gasStationUser);
        fuelOrderService.getUserOrders(username, amount, page);
        verify(fuelOrderRepository).findAllByGasStationUser(eq(gasStationUser), argThat(req -> {
            return req.getPageNumber() == page && req.getPageSize() == amount && req.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testGetTimeOrdersShouldReturnPageableOfFuelOrdersBetweenBeforeAndAfterDates() {
        int page = 1;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        fuelOrderService.getTimeOrders(before, after, amount, page);
        verify(fuelOrderRepository).findAllByOrderDateBetween(eq(before), eq(after), argThat(req -> {
            return req.getPageNumber() == page && req.getPageSize() == amount && req.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testGetFuelTimeOrdersShouldReturnPageableOfFuelOrdersBetweenBeforeAndAfterDatesForSpecifiedFuel() {
        int page = 1;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        fuelOrderService.getFuelTimeOrders(fuelName, before, after, amount, page);
        verify(fuelOrderRepository).findAllByFuelAndOrderDateBetween(eq(fuel), eq(before), eq(after), argThat(req -> {
            return req.getPageNumber() == page && req.getPageSize() == amount && req.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testRemoveFuelOrderShouldDeleteFoundFuelOrderById() {
        given(fuelOrderRepository.findById(fuelOrderId)).willReturn(Optional.of(fuelOrder));
        fuelOrderService.removeFuelOrder(fuelOrderId);
        verify(fuelOrderRepository).delete(fuelOrder);
    }
}
