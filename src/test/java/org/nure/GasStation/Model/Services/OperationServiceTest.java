package org.nure.GasStation.Model.Services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.OperationException;
import org.nure.GasStation.Model.Enumerations.OperationTypes;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.Operation;
import org.nure.GasStation.Model.RepositoryInterfaces.IOperationRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IAdminService;
import org.nure.GasStation.Model.ServiceInterfaces.IFuelService;
import org.nure.GasStation.Model.ServiceInterfaces.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.nure.GasStation.Model.Fuel;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationServiceTest {

    @MockBean
    private IFuelService fuelService;
    @MockBean
    private IOperationRepository operationRepository;
    @MockBean
    private IAdminService adminService;
    @Autowired
    private IOperationService operationService;

    // Default user
    private final String username = "matviei";
    private final String password = "pass1234";
    private final UserRoles userRole = UserRoles.ROLE_ADMIN;
    // Default fuel
    private final String fuelName = "95";
    private final float price = (float) 9.99;
    private final float fuelLeft = 5000;


    @Test
    public void testBuyFuelShouldAddFuelBuyingOperation() throws Exception {
        float fuelAmount = 100;
        given(fuelService.getFuel(fuelName)).willReturn(new Fuel(fuelName, price, fuelLeft));
        given(adminService.getUser(username)).willReturn(new GasStationUser(username, password, userRole));
        operationService.buyFuel(username, fuelName, fuelAmount);
        verify(operationRepository).save(argThat(o -> {
            return OperationTypes.OPERATION_BUY.equals(o.getType());
        }));
    }

    @Test
    public void testBuyFuelShouldRemoveFuelLeftByAmountOfFuelBought() throws Exception {
        float fuelAmount = 100;
        Fuel boughtFuel = new Fuel(fuelName, price, fuelLeft);
        given(fuelService.getFuel(fuelName)).willReturn(boughtFuel);
        given(adminService.getUser(username)).willReturn(new GasStationUser(username, password, userRole));
        operationService.buyFuel(username, fuelName, fuelAmount);
        assertEquals(fuelLeft, boughtFuel.getFuelLeft() + fuelAmount, 0);
    }

    @Test(expected = OperationException.class)
    public void testBuyFuelShouldthrowOperationExceptionIfFuelLeftIsLessThanBuyingAmount() throws Exception {
        float fuelAmount = 20000;
        given(fuelService.getFuel(fuelName)).willReturn(new Fuel(fuelName, price, fuelLeft));
        given(adminService.getUser(username)).willReturn(new GasStationUser(username, password, userRole));
        operationService.buyFuel(username, fuelName, fuelAmount);
    }

    @Test
    public void testFillFuelShouldAddFuelFillingOperation() throws Exception {
        float fuelAmount = 100;
        given(fuelService.getFuel(fuelName)).willReturn(new Fuel(fuelName, price, fuelLeft));
        given(adminService.getUser(username)).willReturn(new GasStationUser(username, password, userRole));
        operationService.fillFuel(username, fuelName, fuelAmount);
        verify(operationRepository).save(argThat(o -> {
           return OperationTypes.OPERATION_FILL.equals(o.getType());
        }));
    }

    @Test
    public void testFillFuelShouldAddFuelByAmout() throws Exception {
        float fuelAmount = 100;
        Fuel boughtFuel = new Fuel(fuelName, price, fuelLeft);
        given(fuelService.getFuel(fuelName)).willReturn(boughtFuel);
        given(adminService.getUser(username)).willReturn(new GasStationUser(username, password, userRole));
        operationService.fillFuel(username, fuelName, fuelAmount);
        assertEquals(fuelLeft, boughtFuel.getFuelLeft() - fuelAmount, 0);
    }

    @Test
    public void testGetOperationByIdShouldReturnOperationByGivenId() {
        long operationId = 23435123;
        Operation foundOperation = new Operation(operationId, 300, new Date(), new Fuel(), new GasStationUser(), OperationTypes.OPERATION_BUY);
        given(operationRepository.findById(operationId)).willReturn(Optional.of(foundOperation));
        Operation operationFinding = operationService.getOperationById(operationId);
        assertEquals(foundOperation, operationFinding);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetOperationByIdShouldThrowEntityNotFoundExceptionIfOperationNotFound() {
        long operationId = 23435123;
        given(operationRepository.findById(operationId)).willReturn(Optional.empty());
        Operation operationFinding = operationService.getOperationById(operationId);
    }

    @Test
    public void testGetOperationsShouldReturnPageableOfOperationInDescendingOrderByDate() {
        int page = 1;
        int amount = 5;
        operationService.getOperations(amount, page);
        verify(operationRepository).findAll((PageRequest) argThat(req -> {
            PageRequest rightRequest = (PageRequest) req;
            return rightRequest.getPageNumber() == page && rightRequest.getPageSize() == amount && rightRequest.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testGetUserOperationsShouldReturnPageableOfUserOperations() {
        int page = 1;
        int amount = 5;
        GasStationUser user = new GasStationUser(username, password, userRole);
        given(adminService.getUser(username)).willReturn(user);
        operationService.getUserOperations(username, amount, page);
        verify(operationRepository).findAllByGasStationUser(eq(user), argThat(req -> {
            return req.getPageNumber() == page && req.getPageSize() == amount && req.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testGetTimeOperationsShouldReturnPageableOfOperationsBetweenBeforeAndAfterDates() {
        int page = 1;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        operationService.getTimeOperations(before, after, amount, page);
        verify(operationRepository).findAllByOperationDateBetween(eq(before), eq(after), argThat(req -> {
            return req.getPageNumber() == page && req.getPageSize() == amount && req.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

    @Test
    public void testGetUserTimeOperationsShouldReturnPageableOfOperationsBetweenBeforeAndAfterDatesForSpecifiedUser() {
        int page = 1;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        GasStationUser user = new GasStationUser(username, password, userRole);
        given(adminService.getUser(username)).willReturn(user);
        operationService.getUserTimeOperations(username, before, after, amount, page);
        verify(operationRepository).findAllByGasStationUserAndOperationDateBetween(eq(user), eq(before), eq(after), argThat(req -> {
            return req.getPageNumber() == page && req.getPageSize() == amount && req.getSort().equals(new Sort(Sort.Direction.DESC, "date"));
        }));
    }

}
