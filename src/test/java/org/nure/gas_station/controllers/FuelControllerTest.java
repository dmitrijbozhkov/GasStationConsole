package org.nure.gas_station.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exchange_models.ListDTO;
import org.nure.gas_station.exchange_models.fuel_controller.CreateFuel;
import org.nure.gas_station.exchange_models.fuel_controller.FuelDetails;
import org.nure.gas_station.exchange_models.fuel_controller.RequestFuel;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelStorage;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FuelControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IFuelService fuelService;

    private final String fuelName = "95";
    private final long fuelTariffId = 36;
    private final float fuelLeft = 10000;
    private static ObjectMapper map;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testAddFuelShouldAddNewFuelAndReturnOkResponse() throws Exception {
        CreateFuel fuel = new CreateFuel(fuelName, fuelTariffId, fuelLeft);
        System.out.println(map.writeValueAsString(fuel));
        mvc.perform(post("/api/fuel/add").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(fuel)))
                .andExpect(status().isOk());
        verify(fuelService).addFuel(fuelName, fuelTariffId, fuelLeft);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testRemoveFuelShouldCallRemoveFuelAndReturnOk() throws Exception {
        RequestFuel fuel = new RequestFuel(fuelName);
        mvc.perform(delete("/api/fuel/remove").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(fuel)))
                .andExpect(status().isOk());
        verify(fuelService).removeFuel(fuelName);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testGetFuelShouldReturnFuelByFuelName() throws Exception {
        RequestFuel fuelRequest = new RequestFuel(fuelName);
        float fuelTariffRate = 345;
        long fuelStorageId = 3;
        FuelStorage fuelStorage = new FuelStorage(fuelStorageId, fuelLeft);
        FuelTariff fuelTariff = new FuelTariff(fuelTariffId, fuelTariffRate);
        Fuel fuel = new Fuel(fuelName, fuelStorage, fuelTariff);
        fuel.setFuelName(fuelName);
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        MvcResult result = mvc.perform(post("/api/fuel/get").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(fuelRequest)))
                .andExpect(status().isOk())
                .andReturn();
        FuelDetails foundFuel = map.readValue(result.getResponse().getContentAsString(), FuelDetails.class);
        assertEquals(fuelName, foundFuel.getFuelName());
        assertEquals(fuelStorageId, foundFuel.getStorage().getId());
        assertEquals(fuelTariffId, foundFuel.getTariff().getId());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testGetFuelsShouldReturnAllFuels() throws Exception {
        String fuelName2 = "92";
        float fuelLeft2 = 355;
        float fuelTariffRate = 345;
        long fuelTariffId2 = 44;
        float fuelTariffRate2 = 333;
        long fuelStorageId = 3;
        long fuelStorageId2 = 4;
        FuelStorage fuelStorage = new FuelStorage(fuelStorageId, fuelLeft);
        FuelStorage fuelStorage2 = new FuelStorage(fuelStorageId2, fuelLeft2);
        FuelTariff fuelTariff = new FuelTariff(fuelTariffId, fuelTariffRate);
        FuelTariff fuelTariff2 = new FuelTariff(fuelTariffId2, fuelTariffRate2);
        Fuel fuel1 = new Fuel(fuelName, fuelStorage, fuelTariff);
        Fuel fuel2 = new Fuel(fuelName2, fuelStorage2, fuelTariff2);
        fuel1.setFuelName(fuelName);
        fuel2.setFuelName(fuelName2);
        given(fuelService.getFuels()).willReturn(Arrays.asList(fuel1, fuel2));
        MvcResult result = mvc.perform(get("/api/fuel/get-all"))
                .andExpect(status().isOk())
                .andReturn();
        ListDTO<FuelDetails> foundFuels = map.readValue(result.getResponse().getContentAsString(), new TypeReference<ListDTO<FuelDetails>>() {});
        Optional<FuelDetails> f1 = foundFuels.getContent()
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName) && f.getStorage().getId() == fuelStorageId && f.getTariff().getId() == fuelTariffId)
                .findFirst();
        Optional<FuelDetails> f2 = foundFuels.getContent()
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName2) && f.getStorage().getId() == fuelStorageId2 && f.getTariff().getId() == fuelTariffId2)
                .findFirst();
        assertTrue(f1.isPresent() && f2.isPresent());
    }
}
