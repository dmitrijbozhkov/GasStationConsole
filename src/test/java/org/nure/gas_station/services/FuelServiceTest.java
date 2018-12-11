package org.nure.gas_station.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelStorage;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.repositories.interfaces.IFuelRepository;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.nure.gas_station.services.interfaces.IFuelTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Temporal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FuelServiceTest {

    @Autowired
    private IFuelService fuelService;
    @MockBean
    private IFuelRepository fuelRepository;
    @MockBean
    private IFuelTariffService fuelTariffService;

    private final String fuelName = "95";
    private final long tariffId = 15;
    private final float fuelLeft = 10000;


    @Test(expected = EntityAlreadyExistsException.class)
    public void testAddFuelShouldThrowEntityAlreadyExistsExceptionIfFuelExists() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(new Fuel()));
        fuelService.addFuel(fuelName, tariffId, fuelLeft);
    }

    @Test
    public void testAddFuelShouldSaveFuelIfFuelNameIsNotPresent() {
        FuelTariff tariff = new FuelTariff();
        given(fuelRepository.findById(fuelName)).willReturn(Optional.empty());
        given(fuelTariffService.getFuelTariff(tariffId)).willReturn(tariff);
        fuelService.addFuel(fuelName, tariffId, fuelLeft);
        verify(fuelRepository).save(argThat(f -> {
            return fuelName.equals(f.getFuelName()) && f.getFuelTariff().equals(tariff) && fuelLeft == f.getFuelStorage().getFuelAmount();
        }));
    }

    @Test
    public void testGetFuelShouldReturnChosenFuelByName() {
        Fuel fuel = new Fuel();
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(fuel));
        Fuel foundFuel = fuelService.getFuel(fuelName);
        assertEquals(foundFuel, fuel);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetFuelShouldThrowEntityNotFoundExceptionIfFuelNotFound() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.empty());
        fuelService.getFuel(fuelName);
    }

    @Test
    public void testGetFuelsShouldReturnAllFuels() {
        Fuel fuel1 = new Fuel();
        Fuel fuel2 = new Fuel();
        given(fuelRepository.findAll()).willReturn(Arrays.asList(fuel1, fuel2));
        List<Fuel> fuelList = fuelService.getFuels();
        Optional<Fuel> firstFuel = fuelList
                .stream()
                .filter(f -> f.equals(fuel1))
                .findFirst();
        Optional<Fuel> secondFuel = fuelList
                .stream()
                .filter(f -> f.equals(fuel2))
                .findFirst();
        assertTrue(firstFuel.isPresent() && secondFuel.isPresent());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testRemoveFuelShouldThrowEntityNotFoundExceptionIfFuelDoesNotExist() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.empty());
        fuelService.removeFuel(fuelName);
    }

    @Test
    public void testRemoveFuelShouldCallDeleteOnFuelRepositoryIfFuelWasFound() {
        Fuel removingFuel = new Fuel();
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(removingFuel));
        fuelService.removeFuel(fuelName);
        verify(fuelRepository).delete(removingFuel);
    }

    @Test
    public void testUpdateFuelLeftShouldSetFuelAmountForGievenFuel() {
        long nextFuelLeft = 55;
        FuelStorage fuelStorage = new FuelStorage(fuelLeft);
        FuelTariff fuelTariff = new FuelTariff(3);
        Fuel updatingFuel = new Fuel(fuelName, fuelStorage, fuelTariff);
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(updatingFuel));
        fuelService.updateFuelLeft(fuelName, nextFuelLeft);
        assertEquals(nextFuelLeft, updatingFuel.getFuelStorage().getFuelAmount(), 0);
    }

    @Test
    public void testUpdateFuelTariffShouldSetNextFuelTariffById() {
        FuelTariff nextFuelTariff = new FuelTariff();
        long nextFuelTariffId = 35;
        FuelStorage fuelStorage = new FuelStorage(fuelLeft);
        FuelTariff fuelTariff = new FuelTariff(3);
        Fuel updatingFuel = new Fuel(fuelName, fuelStorage, fuelTariff);
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(updatingFuel));
        given(fuelTariffService.getFuelTariff(nextFuelTariffId)).willReturn(nextFuelTariff);
        fuelService.updateFuelTariff(fuelName, nextFuelTariffId);
        assertEquals(nextFuelTariff, updatingFuel.getFuelTariff());
    }

    @Test
    public void testUpdateFuelNameShouldSetNextFuelName() {
        String nextFuelName = "92";
        FuelStorage fuelStorage = new FuelStorage(fuelLeft);
        FuelTariff fuelTariff = new FuelTariff(3);
        Fuel updatingFuel = new Fuel(fuelName, fuelStorage, fuelTariff);
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(updatingFuel));
        fuelService.updateFuelName(fuelName, nextFuelName);
        assertEquals(nextFuelName, updatingFuel.getFuelName());
    }

}
