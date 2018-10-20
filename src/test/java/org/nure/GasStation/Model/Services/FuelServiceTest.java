package org.nure.GasStation.Model.Services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.RepositoryInterfaces.IFuelRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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


    @Test(expected = EntityAlreadyExistsException.class)
    public void testAddFuelShouldThrowEntityAlreadyExistsExceptionIfFuelExists() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(new Fuel()));
        fuelService.addFuel(fuelName, price, fuelLeft);
    }

    @Test()
    public void testAddFuelShouldSaveFuelIfFuelNameIsNotPresent() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.empty());
        fuelService.addFuel(fuelName, price, fuelLeft);
        verify(fuelRepository).save(argThat(f -> {
            return fuelName.equals(f.getFuelName()) && price == f.getPrice() && fuelLeft == f.getFuelLeft();
        }));
    }

    @Test
    public void testGetFuelShouldReturnChosenFuelByName() {
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(new Fuel(fuelName, price, fuelLeft)));
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
        given(fuelRepository.findAll()).willReturn(Arrays.asList(new Fuel(fuelName, price, fuelLeft), new Fuel(fuelName2, price2, fuelLeft2)));
        List<Fuel> fuelList = fuelService.getFuels();
        Optional<Fuel> firstFuel = fuelList
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName) && f.getPrice() == price && f.getFuelLeft() == fuelLeft)
                .findFirst();
        Optional<Fuel> secondFuel = fuelList
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName2) && f.getPrice() == price2 && f.getFuelLeft() == fuelLeft2)
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
        Fuel removingFuel = new Fuel(fuelName, price, fuelLeft);
        given(fuelRepository.findById(fuelName)).willReturn(Optional.of(removingFuel));
        fuelService.removeFuel(fuelName);
        verify(fuelRepository).delete(removingFuel);
    }

}
