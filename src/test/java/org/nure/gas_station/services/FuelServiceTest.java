package org.nure.gas_station.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.repositories.interfaces.IFuelRepository;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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

    private final String fuelName = "95";
    private final float price = (float) 9.99;
    private final float fuelLeft = 10000;
    private final float maxFuel = 50000;
    private final String description = "Super fuel";


    @Test(expected = EntityAlreadyExistsException.class)
    public void testAddFuelShouldThrowEntityAlreadyExistsExceptionIfFuelExists() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(new Fuel()));
        fuelService.addFuel(fuelName, price, fuelLeft, maxFuel, description);
    }

    @Test()
    public void testAddFuelShouldSaveFuelIfFuelNameIsNotPresent() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.empty());
        fuelService.addFuel(fuelName, price, fuelLeft, maxFuel, description);
        verify(fuelRepository).save(argThat(f -> {
            return fuelName.equals(f.getFuelName()) && price == f.getPrice() && fuelLeft == f.getFuelLeft();
        }));
    }

    @Test
    public void testGetFuelShouldReturnChosenFuelByName() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(new Fuel(fuelName, price, fuelLeft, maxFuel, description)));
        Fuel foundFuel = fuelService.getFuel(fuelName);
        assertEquals(foundFuel.getFuelName(), fuelName);
        assertEquals(foundFuel.getPrice(), price, 0);
        assertEquals(foundFuel.getFuelLeft(), fuelLeft, 0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetFuelShouldThrowEntityNotFoundExceptionIfFuelNotFound() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.empty());
        fuelService.getFuel(fuelName);
    }

    @Test
    public void testGetFuelsShouldReturnAllFuels() {
        String fuelName2 = "92";
        float price2 = (float) 8.99;
        float fuelLeft2 = 10000;
        float maxFuel2 = 40000;
        String description2 = "Super duper fuel";
        given(fuelRepository.findAll()).willReturn(Arrays.asList(new Fuel(fuelName, price, fuelLeft, maxFuel, description), new Fuel(fuelName2, price2, fuelLeft2, maxFuel2, description2)));
        List<Fuel> fuelList = fuelService.getFuels();
        Optional<Fuel> firstFuel = fuelList
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName) && f.getPrice() == price && f.getFuelLeft() == fuelLeft && f.getMaxFuel() == maxFuel && f.getDescription().equals(description))
                .findFirst();
        Optional<Fuel> secondFuel = fuelList
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName2) && f.getPrice() == price2 && f.getFuelLeft() == fuelLeft2 && f.getMaxFuel() == maxFuel2 && f.getDescription().equals(description2))
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
        Fuel removingFuel = new Fuel(fuelName, price, fuelLeft, maxFuel, description);
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(removingFuel));
        fuelService.removeFuel(fuelName);
        verify(fuelRepository).delete(removingFuel);
    }

}
