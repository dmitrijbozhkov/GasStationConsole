package org.nure.GasStation.Model.Repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FuelRepositoryTest {

    @Test
    public void blank() {
        assertTrue(true);
    }

//    @Autowired
//    private FuelRepository fuelRepository;
//
//    @Test(expected = EntityAlreadyExistsException.class)
//    public void testAddFuelShouldThrowEntityAlreadyExistsExceptionIfFuelExists() {
//        String fuelName = "95";
//        float price = (float) 9.99;
//        float fuelLeft = 10000;
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//    }
//
//    @Test
//    public void testGetFuelShouldReturnChosenFuelByName() {
//        String fuelName = "95";
//        float price = (float) 9.99;
//        float fuelLeft = 10000;
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//        Fuel foundFuel = fuelRepository.getFuel(fuelName);
//        assertEquals(foundFuel.getFuelName(), fuelName);
//        assertEquals(foundFuel.getPrice(), price, 0);
//        assertEquals(foundFuel.getFuelLeft(), fuelLeft, 0);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testGetFuelShouldThrowEntityNotFoundExceptionIfFuelNotFound() {
//        String fuelName = "95";
//        fuelRepository.getFuel(fuelName);
//    }
//
//    @Test
//    public void testGetFuelsShouldReturnAllFuels() {
//        String fuelName1 = "95";
//        float price1 = (float) 9.99;
//        float fuelLeft1 = 10000;
//        String fuelName2 = "92";
//        float price2 = (float) 8.99;
//        float fuelLeft2 = 10000;
//        fuelRepository.addFuel(fuelName1, price1, fuelLeft1);
//        fuelRepository.addFuel(fuelName2, price2, fuelLeft2);
//        ArrayList<Fuel> fuelList = fuelRepository.getFuels();
//        Optional<Fuel> firstFuel = fuelList.stream().filter(f -> f.getFuelName().equals(fuelName1)).findFirst();
//        Optional<Fuel> secondFuel = fuelList.stream().filter(f -> f.getFuelName().equals(fuelName2)).findFirst();
//        assertTrue(firstFuel.isPresent() && secondFuel.isPresent());
//    }
//
//    @Test
//    public void testSetFuelPriceShouldSetPriceOfFuelByName() {
//        String fuelName = "95";
//        float price = (float) 9.99;
//        float fuelLeft = 10000;
//        float nextPrice = (float) 10.99;
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//        fuelRepository.setFuelPrice(fuelName, nextPrice);
//        Fuel changedFuel = fuelRepository.getFuel(fuelName);
//        assertEquals(changedFuel.getPrice(), nextPrice, 0);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testSetFuelPriceShouldThrowEntityNotFoundExceptionIfFuelNotFound() {
//        String fuelName = "95";
//        float nextPrice = (float) 10.99;
//        fuelRepository.setFuelPrice(fuelName, nextPrice);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testRemoveFuelShouldRemoveFuelFromCollection() {
//        String fuelName = "95";
//        float price = (float) 9.99;
//        float fuelLeft = 10000;
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//        fuelRepository.removeFuel(fuelName);
//        fuelRepository.getFuel(fuelName);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testRemoveFuelShouldThrowEntityNotFoundExceptionIfFuelNotFound() {
//        String fuelName = "95";
//        fuelRepository.removeFuel(fuelName);
//    }
//
//    @Test
//    public void testSetFuelLeftShouldSetLeftoverFuelByName() {
//        String fuelName = "95";
//        float price = (float) 9.99;
//        float fuelLeft = 10000;
//        float nextFuelLeft = 9000;
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//        fuelRepository.setFuelLeft(fuelName, nextFuelLeft);
//        Fuel changedFuel = fuelRepository.getFuel(fuelName);
//        assertEquals(changedFuel.getFuelLeft(), nextFuelLeft, 0);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testSetFuelLeftShouldThrowEntityNotFoundExceptionIfFuelNotPresent() {
//        String fuelName = "95";
//        float nextFuelLeft = 9000;
//        fuelRepository.setFuelLeft(fuelName, nextFuelLeft);
//    }
}
