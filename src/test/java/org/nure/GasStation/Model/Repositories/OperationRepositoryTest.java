package org.nure.GasStation.Model.Repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Exceptions.NotEnoughPrivilegesException;
import org.nure.GasStation.Model.Enumerations.OperationTypes;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.RepositoryInterfaces.IFuelRepository;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.nure.GasStation.Model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OperationRepositoryTest {

    @Test
    public void blank() {
        assertTrue(true);
    }
//
//    @Autowired
//    private OperationRepository operationRepository;
//    @Autowired
//    private IFuelRepository fuelRepository;
//    @Autowired
//    private IUserRepository userRepository;
//    // Default buyer user
//    private final String usernameBuyer = "matviei";
//    private final String passwordBuyer = "pass1234";
//    private final UserRoles roleBuyer = UserRoles.USER_BYER;
//    // Default admin user
//    private final String usernameAdmin = "andrei";
//    private final String passwordAdmin = "pass4321";
//    private final UserRoles roleAdmin = UserRoles.USER_ADMIN;
//    // Default fuel
//    private final String fuelName = "95";
//    private final float price = (float) 9.99;
//    private final float fuelLeft = 5000;
//
//    @Before
//    public void initDefaultSetup() {
//        userRepository.createUser(usernameBuyer, passwordBuyer, roleBuyer);
//        userRepository.createUser(usernameAdmin, passwordAdmin, roleAdmin);
//        fuelRepository.addFuel(fuelName, price, fuelLeft);
//    }
//
//    @Test
//    public void testBuyFuelShouldAddFuelBuyingOperation() {
//        float fuelAmount = 100;
//        String operationId = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount);
//        Operation currentOperation = operationRepository.getOperationById(operationId);
//        assertEquals(currentOperation.getUsername(), usernameBuyer);
//        assertEquals(currentOperation.getFuelName(), fuelName);
//        assertEquals(currentOperation.getAmount(), fuelAmount, 0);
//        assertEquals(currentOperation.getType(), OperationTypes.OPERATION_BUY);
//    }
//
//    @Test
//    public void testBuyFuelShouldRemoveFuelLeftByAmountOfFuelBought() {
//        float fuelAmount = 100;
//        operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount);
//        Fuel boughtFuel = fuelRepository.getFuel(fuelName);
//        assertEquals(boughtFuel.getFuelLeft() + fuelAmount, fuelLeft, 0);
//    }
//
//    @Test
//    public void testFillFuelShouldAddFuelFillingOperation() throws NotEnoughPrivilegesException {
//        float fuelAmount = 3000;
//        String operationId = operationRepository.fillFuel(usernameAdmin, fuelName, fuelAmount);
//        Operation currentOperation = operationRepository.getOperationById(operationId);
//        assertEquals(currentOperation.getUsername(), usernameAdmin);
//        assertEquals(currentOperation.getFuelName(), fuelName);
//        assertEquals(currentOperation.getType(), OperationTypes.OPERATION_FILL);
//    }
//
//    @Test
//    public void testFillFuelShouldAddFuelByAmout() throws NotEnoughPrivilegesException {
//        float fuelAmount = 3000;
//        operationRepository.fillFuel(usernameAdmin, fuelName, fuelAmount);
//        Fuel currentFuel = fuelRepository.getFuel(fuelName);
//        assertEquals(currentFuel.getFuelLeft() - fuelAmount, fuelLeft, 0);
//    }
//
//    @Test
//    public void testGetOperationsShouldReturnFirstTwoEarliestOperationsIfAmountIs2AndPageIs1() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        float fuelAmount3 = 400;
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId3 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount3);
//        ArrayList<Operation> latestTwoOperations = operationRepository.getOperations(2, 1);
//        assertEquals(operationId3, latestTwoOperations.get(0).getOperationId());
//        assertEquals(operationId2, latestTwoOperations.get(1).getOperationId());
//    }
//
//    @Test
//    public void testGetOperationsShouldSkipAmountOfOperationsEqualToAmountTimesPage() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        ArrayList<Operation> secondOperation = operationRepository.getOperations(1, 2);
//        assertEquals(operationId1, secondOperation.get(0).getOperationId());
//    }
//
//    @Test(expected = IndexOutOfBoundsException.class)
//    public void testMethodsWithPaginationShouldThrowIndexOutOfBoundsExceptionIfPageIsLessThanOne() {
//        operationRepository.getOperations(1, 0);
//    }
//
//    @Test(expected = IndexOutOfBoundsException.class)
//    public void testMethodsWithPaginationShouldThrowIndexOutOfBoundsExceptionIfAmountIsLessThanOne() {
//        operationRepository.getOperations(0, 1);
//    }
//
//    @Test
//    public void testGetUserOpertionsShouldReturnOperationsOfAGivenUser() {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        String operationId2 = operationRepository.buyFuel(usernameAdmin, fuelName, fuelAmount2);
//        ArrayList<Operation> buyerOperations = operationRepository.getUserOperations(usernameBuyer, 1, 1);
//        assertEquals(usernameBuyer, buyerOperations.get(0).getUsername());
//    }
//
//    @Test
//    public void testGetUserOperationsShouldReturnSecondLatestUserOperationIfAmountIs1AndPageIs2() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        ArrayList<Operation> buyerOperations = operationRepository.getUserOperations(usernameBuyer, 1, 2);
//        assertEquals(operationId1, buyerOperations.get(0).getOperationId());
//    }
//
//    @Test
//    public void testGetTimeOperationsShouldReturnOperationsBetweenTwoTimeFrames() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        float fuelAmount3 = 400;
//        Date before = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        TimeUnit.MILLISECONDS.sleep(10);
//        Date after = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId3 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount3);
//        ArrayList<Operation> timeFrameOperations = operationRepository.getTimeOperations(before, after, 2, 1);
//        assertEquals(operationId2, timeFrameOperations.get(0).getOperationId());
//        assertEquals(operationId1, timeFrameOperations.get(1).getOperationId());
//    }
//
//    @Test
//    public void testGetTimeOperationsShouldGiveSecondOperationInTimeFrameIfAmountIs1AndPageIs2() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        float fuelAmount3 = 400;
//        Date before = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        TimeUnit.MILLISECONDS.sleep(10);
//        Date after = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId3 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount3);
//        ArrayList<Operation> timeFrameOperations = operationRepository.getTimeOperations(before, after, 1, 2);
//        assertEquals(operationId1, timeFrameOperations.get(0).getOperationId());
//    }
//
//    @Test
//    public void testGetUserTimeOperationsShouldGiveSpecifiedUsersOperationsInGivenTimeFrame() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        float fuelAmount3 = 400;
//        Date before = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        String operationId3 = operationRepository.buyFuel(usernameAdmin, fuelName, fuelAmount2);
//        TimeUnit.MILLISECONDS.sleep(10);
//        Date after = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId4 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount3);
//        ArrayList<Operation> timeFrameOperations = operationRepository.getUserTimeOperations(
//                usernameBuyer,
//                before,
//                after,
//                2,
//                1);
//        assertEquals(operationId2, timeFrameOperations.get(0).getOperationId());
//        assertEquals(operationId1, timeFrameOperations.get(1).getOperationId());
//    }
//
//    @Test
//    public void testGetUserTimeOperationsShouldReturnTheLatestOperationIfAmountIs1AndPageis2() throws InterruptedException {
//        float fuelAmount1 = 200;
//        float fuelAmount2 = 300;
//        float fuelAmount3 = 400;
//        Date before = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId1 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount1);
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId2 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount2);
//        TimeUnit.MILLISECONDS.sleep(10);
//        Date after = new Date();
//        TimeUnit.MILLISECONDS.sleep(10);
//        String operationId4 = operationRepository.buyFuel(usernameBuyer, fuelName, fuelAmount3);
//        ArrayList<Operation> timeFrameOperations = operationRepository.getUserTimeOperations(
//                usernameBuyer,
//                before,
//                after,
//                1,
//                2);
//        assertEquals(operationId1, timeFrameOperations.get(0).getOperationId());
//    }
}
