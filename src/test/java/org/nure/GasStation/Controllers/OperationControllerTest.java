package org.nure.GasStation.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Model.Enumerations.OperationTypes;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.ExchangeModels.GasStationPage;
import org.nure.GasStation.Model.ExchangeModels.OperationController.*;
import org.nure.GasStation.Model.Fuel;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.Operation;
import org.nure.GasStation.Model.Services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class OperationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OperationService operationService;

    private static ObjectMapper map;

    private GasStationUser user;
    private Fuel fuel;
    private Operation operation;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    @Before
    public void setUp() {
        this.user = new GasStationUser("matviei", "pass1234", "Matviei", "Servull", UserRoles.ROLE_ADMIN);
        this.fuel = new Fuel("95", 20, 6000);
        this.operation = new Operation(134, 200, 90, new Date(), this.fuel, this.user, OperationTypes.OPERATION_BUY);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testBuyFuelShouldBuyFuelAsCurrentUser() throws Exception {
        int amount = 500;
        OperationRequest operationRequest = new OperationRequest(fuel.getFuelName(), amount);
        mvc.perform(post("/api/operation/buy-fuel").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationRequest)))
                .andExpect(status().isOk());
        verify(operationService).buyFuel("matviei", fuel.getFuelName(), amount);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testFillFuelShouldFillFuelAsCurrentUser() throws Exception {
        int amount = 500;
        OperationRequest operationRequest = new OperationRequest(fuel.getFuelName(), amount);
        mvc.perform(post("/api/operation/fill-fuel").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationRequest)))
                .andExpect(status().isOk());
        verify(operationService).fillFuel("matviei", fuel.getFuelName(), amount);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testGetOperationShouldReturnOperationResponseByProvidedUsername() throws Exception {
        given(operationService.getOperationById(operation.getOperationId())).willReturn(operation);
        MvcResult result = mvc.perform(get("/api/operation/get/" + operation.getOperationId()))
                .andExpect(status().isOk())
                .andReturn();
        OperationResponse response = map.readValue(result.getResponse().getContentAsString(), OperationResponse.class);
        assertEquals(response.getOperationId(), operation.getOperationId());
        assertEquals(response.getAmount(), operation.getAmount(), 0);
        assertEquals(response.getFuelName(), operation.getFuel().getFuelName());
        assertEquals(response.getPrice(), operation.getPrice(), 0);
        assertEquals(response.getType(), operation.getType());
        assertEquals(response.getUsername(), operation.getGasStationUser().getUsername());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testQueryOperationsShouldReturnGasStaionPageWithOperations() throws Exception {
        int page = 0;
        int amount = 5;
        QueryOperationsPage operationsPage = new QueryOperationsPage();
        operationsPage.setPage(page);
        operationsPage.setAmount(amount);
        List<Operation> operationList = Arrays.asList(operation);
        given(operationService.getOperations(amount, page)).willReturn(new PageImpl<Operation>(operationList, new PageRequest(page, amount), operationList.size()));
        MvcResult result = mvc.perform(post("/api/operation/query-pages").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationsPage)))
                .andExpect(status().isOk())
                .andReturn();
        GasStationPage<OperationResponse> response = map.readValue(result.getResponse().getContentAsString(), new TypeReference<GasStationPage<OperationResponse>>() {});
        Optional<OperationResponse> or = response
                .getContent()
                .stream()
                .filter(o -> {
                    return o.getOperationId() == operation.getOperationId() &&
                                    o.getAmount() == operation.getAmount() &&
                                    o.getFuelName().equals(operation.getFuel().getFuelName()) &&
                                    o.getPrice() == operation.getPrice() &&
                                    o.getType().equals(operation.getType()) &&
                                    o.getUsername().equals(operation.getGasStationUser().getUsername());
                })
                .findFirst();
        assertTrue(or.isPresent());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testQueryUserPagesShouldReturnOperationsOfSetUser() throws Exception {
        int page = 0;
        int amount = 5;
        QueryOperationsUser operationsPage = new QueryOperationsUser();
        operationsPage.setPage(page);
        operationsPage.setAmount(amount);
        operationsPage.setUsername(user.getUsername());
        List<Operation> operationList = Arrays.asList(operation);
        given(operationService.getUserOperations(user.getUsername(), amount, page)).willReturn(new PageImpl<Operation>(operationList, new PageRequest(page, amount), operationList.size()));
        MvcResult result = mvc.perform(post("/api/operation/query-user-pages").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationsPage)))
                .andExpect(status().isOk())
                .andReturn();
        GasStationPage<OperationResponse> response = map.readValue(result.getResponse().getContentAsString(), new TypeReference<GasStationPage<OperationResponse>>() {});
        Optional<OperationResponse> or = response
                .getContent()
                .stream()
                .filter(o -> {
                    return o.getOperationId() == operation.getOperationId() &&
                            o.getAmount() == operation.getAmount() &&
                            o.getFuelName().equals(operation.getFuel().getFuelName()) &&
                            o.getPrice() == operation.getPrice() &&
                            o.getType().equals(operation.getType()) &&
                            o.getUsername().equals(operation.getGasStationUser().getUsername());
                })
                .findFirst();
        assertTrue(or.isPresent());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testGetTimeOperationsShouldReturnOperationsBetweenDates() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        QueryOperationsDate operationsPage = new QueryOperationsDate();
        operationsPage.setPage(page);
        operationsPage.setAmount(amount);
        operationsPage.setAfter(after);
        operationsPage.setBefore(before);
        List<Operation> operationList = Arrays.asList(operation);
        given(operationService.getTimeOperations(before, after, amount, page)).willReturn(new PageImpl<Operation>(operationList, new PageRequest(page, amount), operationList.size()));
        MvcResult result = mvc.perform(post("/api/operation/query-date-pages").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationsPage)))
                .andExpect(status().isOk())
                .andReturn();
        GasStationPage<OperationResponse> response = map.readValue(result.getResponse().getContentAsString(), new TypeReference<GasStationPage<OperationResponse>>() {});
        Optional<OperationResponse> or = response
                .getContent()
                .stream()
                .filter(o -> {
                    return o.getOperationId() == operation.getOperationId() &&
                            o.getAmount() == operation.getAmount() &&
                            o.getFuelName().equals(operation.getFuel().getFuelName()) &&
                            o.getPrice() == operation.getPrice() &&
                            o.getType().equals(operation.getType()) &&
                            o.getUsername().equals(operation.getGasStationUser().getUsername());
                })
                .findFirst();
        assertTrue(or.isPresent());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testQueryUserDateOperationsShouldReturnOperationsPageOfUserOperationsBetweenDates() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        QueryOperationsUserDate operationsPage = new QueryOperationsUserDate();
        operationsPage.setPage(page);
        operationsPage.setAmount(amount);
        operationsPage.setAfter(after);
        operationsPage.setBefore(before);
        operationsPage.setUsername(user.getUsername());
        List<Operation> operationList = Arrays.asList(operation);
        given(operationService.getUserTimeOperations(user.getUsername(), before, after, amount, page)).willReturn(new PageImpl<Operation>(operationList, new PageRequest(page, amount), operationList.size()));
        MvcResult result = mvc.perform(post("/api/operation/query-user-date-pages").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationsPage)))
                .andExpect(status().isOk())
                .andReturn();
        GasStationPage<OperationResponse> response = map.readValue(result.getResponse().getContentAsString(), new TypeReference<GasStationPage<OperationResponse>>() {});
        Optional<OperationResponse> or = response
                .getContent()
                .stream()
                .filter(o -> {
                    return o.getOperationId() == operation.getOperationId() &&
                            o.getAmount() == operation.getAmount() &&
                            o.getFuelName().equals(operation.getFuel().getFuelName()) &&
                            o.getPrice() == operation.getPrice() &&
                            o.getType().equals(operation.getType()) &&
                            o.getUsername().equals(operation.getGasStationUser().getUsername());
                })
                .findFirst();
        assertTrue(or.isPresent());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testQueryMyOperationsShouldReturnCurrentUserOperationsPage() throws Exception {
        int page = 0;
        int amount = 5;
        QueryOperationsPage operationsPage = new QueryOperationsPage();
        operationsPage.setPage(page);
        operationsPage.setAmount(amount);
        List<Operation> operationList = Arrays.asList(operation);
        given(operationService.getUserOperations("matviei", amount, page)).willReturn(new PageImpl<Operation>(operationList, new PageRequest(page, amount), operationList.size()));
        MvcResult result = mvc.perform(post("/api/operation/query-my-pages").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationsPage)))
                .andExpect(status().isOk())
                .andReturn();
        GasStationPage<OperationResponse> response = map.readValue(result.getResponse().getContentAsString(), new TypeReference<GasStationPage<OperationResponse>>() {});
        Optional<OperationResponse> or = response
                .getContent()
                .stream()
                .filter(o -> {
                    return o.getOperationId() == operation.getOperationId() &&
                            o.getAmount() == operation.getAmount() &&
                            o.getFuelName().equals(operation.getFuel().getFuelName()) &&
                            o.getPrice() == operation.getPrice() &&
                            o.getType().equals(operation.getType()) &&
                            o.getUsername().equals(operation.getGasStationUser().getUsername());
                })
                .findFirst();
        assertTrue(or.isPresent());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testQueryMyDateOperationsShouldReturnCurrentUserOperationsPageBetweenDates() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = new Date();
        Date after = new Date();
        QueryOperationsDate operationsPage = new QueryOperationsDate();
        operationsPage.setPage(page);
        operationsPage.setAmount(amount);
        operationsPage.setBefore(before);
        operationsPage.setAfter(after);
        List<Operation> operationList = Arrays.asList(operation);
        given(operationService.getUserTimeOperations("matviei", before, after, amount, page)).willReturn(new PageImpl<Operation>(operationList, new PageRequest(page, amount), operationList.size()));
        MvcResult result = mvc.perform(post("/api/operation/query-my-date-pages").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(operationsPage)))
                .andExpect(status().isOk())
                .andReturn();
        GasStationPage<OperationResponse> response = map.readValue(result.getResponse().getContentAsString(), new TypeReference<GasStationPage<OperationResponse>>() {});
        Optional<OperationResponse> or = response
                .getContent()
                .stream()
                .filter(o -> {
                    return o.getOperationId() == operation.getOperationId() &&
                            o.getAmount() == operation.getAmount() &&
                            o.getFuelName().equals(operation.getFuel().getFuelName()) &&
                            o.getPrice() == operation.getPrice() &&
                            o.getType().equals(operation.getType()) &&
                            o.getUsername().equals(operation.getGasStationUser().getUsername());
                })
                .findFirst();
        assertTrue(or.isPresent());
    }
}
