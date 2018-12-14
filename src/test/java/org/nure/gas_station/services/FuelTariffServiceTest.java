package org.nure.gas_station.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.OperationException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.repositories.interfaces.IFuelTariffRepository;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.nure.gas_station.services.interfaces.IFuelTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuelTariffServiceTest {

    @Autowired
    private IFuelTariffService fuelTariffService;

    @MockBean
    private IFuelTariffRepository fuelTariffRepository;
    @MockBean
    private IFuelService fuelService;

    private final float tariff = (float) 9.99;
    private final long tariffId = 99;

    @Test
    public void testGetFuelTariffShouldGetFuelTariffByGivenId() {
        FuelTariff fuelTariff = new FuelTariff();
        given(fuelTariffRepository.findById(tariffId)).willReturn(Optional.of(fuelTariff));
        FuelTariff tariffFound = fuelTariffService.getFuelTariff(tariffId);
        assertEquals(fuelTariff, tariffFound);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetFuelShouldThrowEntityNotFoundExceptionIfFuelNotFound() {
        given(fuelTariffRepository.findById(tariffId)).willReturn(Optional.empty());
        fuelTariffService.getFuelTariff(tariffId);
    }

    @Test
    public void testRemoveFuelTariffShouldCallDeleteOnFoundFuelTariffIfFuelIsNull() throws OperationException {
        FuelTariff fuelTariff = new FuelTariff();
        given(fuelTariffRepository.findById(tariffId)).willReturn(Optional.of(fuelTariff));
        fuelTariffService.removeFuelTariff(tariffId);
        verify(fuelTariffRepository).delete(eq(fuelTariff));
    }

    @Test(expected = OperationException.class)
    public void testRemoveFuelTariffShouldThrowOperationExceptionIfFuelIsNotNull() throws OperationException {
        FuelTariff fuelTariff = new FuelTariff();
        fuelTariff.setFuel(new Fuel());
        given(fuelTariffRepository.findById(tariffId)).willReturn(Optional.of(fuelTariff));
        fuelTariffService.removeFuelTariff(tariffId);
        verify(fuelTariffRepository).delete(eq(fuelTariff));
    }

    @Test
    public void testUpdateFuelTariffShouldSetFelTariffExchangeRate() {
        String fuelName = "98";
        float nextRate = (float) 9.98;
        Fuel fuel = new Fuel();
        FuelTariff fuelTariff = new FuelTariff(tariffId, tariff);
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        given(fuelTariffRepository.findById(tariffId)).willReturn(Optional.of(fuelTariff));
        fuelTariffService.updateFuelTariff(tariffId, nextRate);
        assertEquals(nextRate, fuelTariff.getExchangeRate(),0);
    }

    @Test
    public void createFuelTariffShouldSaveNewFuelTariff() {
        given(fuelTariffRepository.save(any(FuelTariff.class))).willReturn(new FuelTariff(tariffId, tariff));
        fuelTariffService.createFuelTariff(tariff);
        verify(fuelTariffRepository).save(argThat(t -> {
            return t.getExchangeRate() == tariff;
        }));
    }

    @Test
    public void createFuelTariffShouldReturnNewFuelTariff() {
        given(fuelTariffRepository.save(any(FuelTariff.class))).willReturn(new FuelTariff(tariffId, tariff));
        FuelTariff nextFuelTariff = fuelTariffService.createFuelTariff(tariff);
        assertEquals(tariffId, (long) nextFuelTariff.getId());
        assertEquals(tariff, nextFuelTariff.getExchangeRate(), 0);
    }

}
