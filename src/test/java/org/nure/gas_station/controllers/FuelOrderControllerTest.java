package org.nure.gas_station.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.nure.gas_station.exchange_models.PageDTO;
import org.nure.gas_station.exchange_models.fuel_order_controller.order.CreateOrder;
import org.nure.gas_station.exchange_models.fuel_order_controller.order.OrderDetails;
import org.nure.gas_station.exchange_models.fuel_order_controller.query.*;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelVolumeOfSales;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelsVolumeOfSales;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.FuelsVolumeOfSalesRequest;
import org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales.VolumeOfSalesRequest;
import org.nure.gas_station.model.*;
import org.nure.gas_station.model.enumerations.OrderType;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.services.implementations.FuelOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FuelOrderControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FuelOrderService fuelOrderService;

    private static ObjectMapper map;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    // Default fuels
    private final String fuelName = "95";
    private final String secondFuelName = "92";
    private final long tariffId = 15;
    private final float exchangeRate = (float) 9.99;
    private long secondFuelTariffId = 99;
    private float secondExchangeRate = (float) 8.99;
    private final long fuelStorageId = 99;
    private final float fuelLeft = 5000;
    private final long secondFuelStorageId = 89;
    private final float secondFuelLeft = 3000;
    private Fuel fuel;
    private Fuel secondFuel;
    // Default users
    private final String username = "matviei";
    private final String password = "pass1234";
    private final String name = "pepe";
    private final String surname = "keke";
    private final UserRoles userRole = UserRoles.ROLE_ADMIN;
    private final String secondUsername = "dima";
    private final String secondPassword = "pass12345";
    private final String secondName = "pepek";
    private final String secondSurname = "kekes";
    private final UserRoles secondUserRole = UserRoles.ROLE_BUYER;
    private GasStationUser gasStationUser;
    private GasStationUser secondGasStationUser;
    // Default orders
    private final long fuelOrderId = 33;
    private final float orderAmount = FuelOrder.getMaxOrderVolume() - 1;
    private final OrderType orderType = OrderType.CURRENCY_BY_FUEL;
    private final Date orderDate = Date.from(Instant.now().plus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS)); // serialization doesn't accept milliseconds
    private final long secondFuelOrderId = 32;
    private final float secondOrderAmount = FuelOrder.getMaxOrderVolume() - 2;
    private final OrderType secondOrderType = OrderType.CURRENCY_BY_FUEL;
    private final Date secondOrderDate = Date.from(Instant.now().plus(2, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS)); // serialization doesn't accept milliseconds
    private FuelOrder fuelOrder;
    private FuelOrder secondFuelOrder;

    @Before
    public void setUpOrderData() {
        FuelStorage storage = new FuelStorage(fuelStorageId, fuelLeft);
        FuelStorage secondFuelStorage = new FuelStorage(secondFuelStorageId, secondFuelLeft);
        FuelTariff fuelTariff = new FuelTariff(tariffId, exchangeRate);
        FuelTariff secondFuelTariff = new FuelTariff(secondFuelTariffId, secondExchangeRate);
        this.fuel = new Fuel(fuelName, storage, fuelTariff);
        this.secondFuel = new Fuel(secondFuelName, secondFuelStorage, secondFuelTariff);
        this.gasStationUser = new GasStationUser(username, password, name, surname, userRole);
        this.secondGasStationUser = new GasStationUser(secondUsername, secondPassword, secondName, secondSurname, secondUserRole);
        this.fuelOrder = new FuelOrder(fuelOrderId, orderAmount, orderType, orderDate, this.fuel, fuelTariff, this.gasStationUser);
        this.secondFuelOrder = new FuelOrder(secondFuelOrderId, secondOrderAmount, secondOrderType, secondOrderDate, this.secondFuel, secondFuelTariff, this.secondGasStationUser);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testCreateOrderShouldCreateNewOrderWithCurrentLoggedInUser() throws Exception {
        CreateOrder createOrder = new CreateOrder(orderAmount, orderType, orderDate, fuelName);
        mvc.perform(post("/api/order/create").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(createOrder)))
                .andExpect(status().isOk());
        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> fuelNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Float> orderAmountCaptor = ArgumentCaptor.forClass(Float.class);
        ArgumentCaptor<OrderType> orderTypeCaptor = ArgumentCaptor.forClass(OrderType.class);
        ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        verify(fuelOrderService).orderFuel(usernameCaptor.capture(), fuelNameCaptor.capture(), orderAmountCaptor.capture(), orderTypeCaptor.capture(), dateCaptor.capture());
        assertEquals(username, usernameCaptor.getValue());
        assertEquals(fuelName, fuelNameCaptor.getValue());
        assertEquals(orderAmount, orderAmountCaptor.getValue(), 0);
        assertEquals(orderType, orderTypeCaptor.getValue());
        assertEquals(orderDate, dateCaptor.getValue());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testGetOrderShouldReturnOrderDetailsByProvidedId() throws Exception {
        given(fuelOrderService.getOrderById(fuelOrderId)).willReturn(fuelOrder);
        MvcResult result = mvc.perform(get("/api/order/get/" + fuelOrderId))
                .andExpect(status().isOk())
                .andReturn();
        OrderDetails details = map.readValue(result.getResponse().getContentAsString(), OrderDetails.class);
        assertEquals(orderAmount, details.getAmount(), 0);
        assertEquals(orderDate, details.getOrderDate());
        assertEquals(orderType, details.getOrderType());
        assertEquals(fuelName, details.getFuel().getFuelName());
        assertEquals(tariffId, details.getTariff().getId());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testRemoveOrderShouldCallRemoveFuelOrderOnServiceByProvidedId() throws Exception {
        mvc.perform(delete("/api/order/remove/" + fuelOrderId))
                .andExpect(status().is(204));
        verify(fuelOrderService).removeFuelOrder(fuelOrderId);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testQueryOrdersShouldReturnNewestOrdersByPageAndAmount() throws Exception {
        int page = 0;
        int amount = 5;
        List<FuelOrder> orders = Arrays.asList(fuelOrder, secondFuelOrder);
        Page pages = new PageImpl<FuelOrder>(orders, PageRequest.of(page, amount), orders.size());
        given(fuelOrderService.getOrders(amount, page)).willReturn(pages);
        QueryOrders queryOrders = new QueryOrders(page, amount);
        MvcResult result = mvc.perform(post("/api/order/query").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(queryOrders)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<OrderDetails> orderDetails = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<OrderDetails>>() {
        });
        assertEquals(page, orderDetails.getPage());
        assertEquals(amount, orderDetails.getAmount());
        assertEquals(2, orderDetails.getTotal());
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == fuelOrderId));
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == secondFuelOrderId));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testQueryUserOrdersShouldReturnNewestUserOrdersByPageAmountAndUsername() throws Exception {
        int page = 0;
        int amount = 5;
        List<FuelOrder> orders = Arrays.asList(secondFuelOrder);
        Page pages = new PageImpl<FuelOrder>(orders, PageRequest.of(page, amount), orders.size());
        given(fuelOrderService.getUserOrders(secondUsername, amount, page)).willReturn(pages);
        QueryUserOrders queryUserOrders = new QueryUserOrders(page, amount, secondUsername);
        MvcResult result = mvc.perform(post("/api/order/query-user").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(queryUserOrders)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<OrderDetails> orderDetails = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<OrderDetails>>() {
        });
        assertEquals(page, orderDetails.getPage());
        assertEquals(amount, orderDetails.getAmount());
        assertEquals(orders.size(), orderDetails.getTotal());
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == secondFuelOrderId));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testQueryCurrentUserOrdersShouldReturnNewestUserOrdersOfCurrentUserByPageAndAmount() throws Exception {
        int page = 0;
        int amount = 5;
        List<FuelOrder> orders = Arrays.asList(fuelOrder);
        Page pages = new PageImpl<FuelOrder>(orders, PageRequest.of(page, amount), orders.size());
        given(fuelOrderService.getUserOrders(username, amount, page)).willReturn(pages);
        QueryOrders queryOrders = new QueryOrders(page, amount);
        MvcResult result = mvc.perform(post("/api/order/query-user-current").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(queryOrders)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<OrderDetails> orderDetails = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<OrderDetails>>() {
        });
        assertEquals(page, orderDetails.getPage());
        assertEquals(amount, orderDetails.getAmount());
        assertEquals(orders.size(), orderDetails.getTotal());
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == fuelOrderId));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testQueryDateOrdersShouldReturnNewestOrdersBetweenGivenDatesWithPageAndAmount() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = Date.from(Instant.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        Date after = Date.from(Instant.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        List<FuelOrder> orders = Arrays.asList(fuelOrder, secondFuelOrder);
        Page pages = new PageImpl<FuelOrder>(orders, PageRequest.of(page, amount), orders.size());
        given(fuelOrderService.getTimeOrders(before, after, amount, page)).willReturn(pages);
        QueryDateOrders queryOrders = new QueryDateOrders(page, amount, before, after);
        MvcResult result = mvc.perform(post("/api/order/query-date").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(queryOrders)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<OrderDetails> orderDetails = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<OrderDetails>>() {
        });
        assertEquals(page, orderDetails.getPage());
        assertEquals(amount, orderDetails.getAmount());
        assertEquals(orders.size(), orderDetails.getTotal());
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == fuelOrderId));
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == secondFuelOrderId));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testQueryFuelOrdersShouldReturnNewestOrdersOfGivenFuelWithPageAndAmount() throws Exception {
        int page = 0;
        int amount = 5;
        List<FuelOrder> orders = Arrays.asList(fuelOrder);
        Page pages = new PageImpl<FuelOrder>(orders, PageRequest.of(page, amount), orders.size());
        given(fuelOrderService.getFuelOrders(fuelName, amount, page)).willReturn(pages);
        QueryFuelOrders queryOrders = new QueryFuelOrders(page, amount, fuelName);
        MvcResult result = mvc.perform(post("/api/order/query-fuel").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(queryOrders)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<OrderDetails> orderDetails = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<OrderDetails>>() {
        });
        assertEquals(page, orderDetails.getPage());
        assertEquals(amount, orderDetails.getAmount());
        assertEquals(orders.size(), orderDetails.getTotal());
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == fuelOrderId));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testQueryFuelDateOrdersShouldReturnNewestOrdersOfGivenFuelBetweenDatesWithPageAndAmount() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = Date.from(Instant.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        Date after = Date.from(Instant.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        List<FuelOrder> orders = Arrays.asList(fuelOrder);
        Page pages = new PageImpl<FuelOrder>(orders, PageRequest.of(page, amount), orders.size());
        given(fuelOrderService.getFuelTimeOrders(fuelName, before, after, amount, page)).willReturn(pages);
        QueryFuelDateOrders queryOrders = new QueryFuelDateOrders(page, amount, before, after, fuelName);
        MvcResult result = mvc.perform(post("/api/order/query-fuel-date").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(queryOrders)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<OrderDetails> orderDetails = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<OrderDetails>>() {
        });
        assertEquals(page, orderDetails.getPage());
        assertEquals(amount, orderDetails.getAmount());
        assertEquals(orders.size(), orderDetails.getTotal());
        assertTrue(orderDetails.getContent().stream().anyMatch(c -> c.getId() == fuelOrderId));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = {"ROLE_ADMIN"})
    public void testGetVolumeOfSalesForFuelsShouldReturnEachFuelsVolumeOfSalesAndSumOfThem() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = Date.from(Instant.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        Date after = Date.from(Instant.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        List<String> fuelNames = Arrays.asList(fuelName, secondFuelName);
        List<FuelVolumeOfSales> fuelVolumeOfSales = new ArrayList<>(); // Prepare volume of sales for each fuel
        fuelVolumeOfSales.add(new FuelVolumeOfSales(fuel, Arrays.asList(fuelOrder)));
        fuelVolumeOfSales.add(new FuelVolumeOfSales(secondFuel, Arrays.asList(secondFuelOrder)));
        FuelsVolumeOfSales fuelsVolumeOfSales = new FuelsVolumeOfSales(fuelVolumeOfSales);
        given(fuelOrderService.getFuelsVolumeOfSalesBetweenDates(fuelNames, before, after)).willReturn(fuelsVolumeOfSales); // Mock service method
        FuelsVolumeOfSalesRequest volumeOfSalesRequest = new FuelsVolumeOfSalesRequest(before, after, fuelNames);
        MvcResult result = mvc.perform(post("/api/order/volume-of-sales-fuel").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(volumeOfSalesRequest)))
                .andExpect(status().isOk())
                .andReturn();
        FuelsVolumeOfSales volumeOfSales = map.readValue(result.getResponse().getContentAsString(), FuelsVolumeOfSales.class);
        assertEquals((orderAmount * exchangeRate) + (secondOrderAmount * secondExchangeRate), volumeOfSales.getOverallVolumeOfSales(), 0);
        assertTrue(volumeOfSales.getFuelVolumeOfSales().stream()
                .anyMatch(c -> c.getFuelName().equals(fuelName) && c.getVolumeOfSales() == orderAmount * exchangeRate));
        assertTrue(volumeOfSales.getFuelVolumeOfSales().stream().anyMatch(c -> c.getFuelName().equals(secondFuelName) && c.getVolumeOfSales() == secondOrderAmount * secondExchangeRate));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testGetVolumeOfSalesShouldReturnEachFuelsVolumeOfSalesAndSumOfThem() throws Exception {
        int page = 0;
        int amount = 5;
        Date before = Date.from(Instant.now().minus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        Date after = Date.from(Instant.now().minus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS));
        List<FuelVolumeOfSales> fuelVolumeOfSales = new ArrayList<>(); // Prepare volume of sales for each fuel
        fuelVolumeOfSales.add(new FuelVolumeOfSales(fuel, Arrays.asList(fuelOrder)));
        fuelVolumeOfSales.add(new FuelVolumeOfSales(secondFuel, Arrays.asList(secondFuelOrder)));
        FuelsVolumeOfSales fuelsVolumeOfSales = new FuelsVolumeOfSales(fuelVolumeOfSales);
        given(fuelOrderService.getVolumeOfSalesBetweenDates(before, after)).willReturn(fuelsVolumeOfSales); // Mock service method
        VolumeOfSalesRequest volumeOfSalesRequest = new VolumeOfSalesRequest(before, after);
        MvcResult result = mvc.perform(post("/api/order/volume-of-sales").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(volumeOfSalesRequest)))
                .andExpect(status().isOk())
                .andReturn();
        FuelsVolumeOfSales volumeOfSales = map.readValue(result.getResponse().getContentAsString(), FuelsVolumeOfSales.class);
        assertEquals((orderAmount * exchangeRate) + (secondOrderAmount * secondExchangeRate), volumeOfSales.getOverallVolumeOfSales(), 0);
        assertTrue(volumeOfSales.getFuelVolumeOfSales().stream()
                .anyMatch(c -> c.getFuelName().equals(fuelName) && c.getVolumeOfSales() == orderAmount * exchangeRate));
        assertTrue(volumeOfSales.getFuelVolumeOfSales().stream().anyMatch(c -> c.getFuelName().equals(secondFuelName) && c.getVolumeOfSales() == secondOrderAmount * secondExchangeRate));
    }
}