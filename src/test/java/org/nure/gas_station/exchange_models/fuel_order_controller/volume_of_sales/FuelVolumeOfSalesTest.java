package org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.enumerations.OrderType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FuelVolumeOfSalesTest {

    private FuelVolumeOfSales fuelVolumeOfSales;
    private final float amount = (float) 9.99;
    private final float exchangeRate = (float) 2.99;

    @Before
    public void setFuelVolumeOfSales() {
        this.fuelVolumeOfSales = new FuelVolumeOfSales();
    }

    @Test
    public void testSetVolumeOfSalesShouldGetFuelOrdersAndExchangeRateAndSetVolumeOfSalesEqualsToAmountOfFuelMultipliedByExchangeRateIfOrderTypeIsCurrencyByFuel() {
        FuelOrder fuelOrder = new FuelOrder();
        fuelOrder.setOrderType(OrderType.CURRENCY_BY_FUEL);
        fuelOrder.setAmount(amount);
        fuelVolumeOfSales.setVolumeOfSales(Arrays.asList(fuelOrder), exchangeRate);
        assertEquals(amount * exchangeRate, fuelVolumeOfSales.getVolumeOfSales(), 0);
    }

    @Test
    public void testSetVolumeOfSalesShouldGetFuelOrdersAndExchangeRateAndSetVolumeOfSalesEqualsToAmountOfMoneyIfOrderTypeIsFuelByCurrency() {
        FuelOrder fuelOrder = new FuelOrder();
        fuelOrder.setOrderType(OrderType.FUEL_BY_CURRENCY);
        fuelOrder.setAmount(amount);
        fuelVolumeOfSales.setVolumeOfSales(Arrays.asList(fuelOrder), exchangeRate);
        assertEquals(amount, fuelVolumeOfSales.getVolumeOfSales(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void testSetVolumeOfSalesShouldThrowRuntimeExceptionIfOrderTypeIsNotCurrencyByFuelOrFuelByCurrency() {
        FuelOrder fuelOrder = new FuelOrder();
        fuelOrder.setOrderType(null);
        fuelOrder.setAmount(amount);
        fuelVolumeOfSales.setVolumeOfSales(Arrays.asList(fuelOrder), exchangeRate);
    }

}
