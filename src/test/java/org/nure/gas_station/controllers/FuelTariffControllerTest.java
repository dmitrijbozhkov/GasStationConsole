package org.nure.gas_station.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exchange_models.fuel_controller.ChangeFuelName;
import org.nure.gas_station.exchange_models.fuel_controller.CreateFuel;
import org.nure.gas_station.exchange_models.fuel_controller.FuelDetails;
import org.nure.gas_station.exchange_models.fuel_controller.RequestFuel;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.CreateTariff;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.TariffDetails;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.TariffFuelDetails;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelStorage;
import org.nure.gas_station.model.FuelTariff;
import org.nure.gas_station.services.interfaces.IFuelService;
import org.nure.gas_station.services.interfaces.IFuelTariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FuelTariffControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IFuelTariffService fuelTariffService;

    private final long fuelTariffId = 33;
    private final float exchangeRate = (float) 9.99;
    private static ObjectMapper map;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testAddTariffShouldAddNewFuelTariff() throws Exception {
        CreateTariff createTariff = new CreateTariff(exchangeRate);
        FuelTariff fuelTariff = new FuelTariff(fuelTariffId, exchangeRate);
        given(fuelTariffService.createFuelTariff(exchangeRate)).willReturn(fuelTariff);
        mvc.perform(post("/api/tariff/add").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(createTariff)))
                .andExpect(status().isOk());
        verify(fuelTariffService).createFuelTariff(exchangeRate);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testAddTariffShouldReturnNewFuelTariffDetails() throws Exception {
        CreateTariff createTariff = new CreateTariff(exchangeRate);
        FuelTariff fuelTariff = new FuelTariff(fuelTariffId, exchangeRate);
        given(fuelTariffService.createFuelTariff(exchangeRate)).willReturn(fuelTariff);
        MvcResult result = mvc.perform(post("/api/tariff/add").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(createTariff)))
                .andExpect(status().isOk())
                .andReturn();
        TariffDetails tariffDetails = map.readValue(result.getResponse().getContentAsString(), TariffDetails.class);
        assertEquals(fuelTariffId, tariffDetails.getId());
        assertEquals(exchangeRate, tariffDetails.getExchangeRate(), 0);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testRemoveTariffShouldCallRemoveFuelTariffAndReturnOk() throws Exception {
        mvc.perform(delete("/api/tariff/remove/" + fuelTariffId))
                .andExpect(status().is(204));
        verify(fuelTariffService).removeFuelTariff(fuelTariffId);
    }

    @Test
    public void testGetTariffShouldReturnFuelTariffByTariffId() throws Exception {
        FuelTariff fuelTariff = new FuelTariff(fuelTariffId, exchangeRate);
        given(fuelTariffService.getFuelTariff(fuelTariffId)).willReturn(fuelTariff);
        MvcResult result = mvc.perform(post("/api/tariff/get/" + fuelTariffId))
                .andExpect(status().isOk())
                .andReturn();
        TariffFuelDetails tariffDetails = map.readValue(result.getResponse().getContentAsString(), TariffFuelDetails.class);
        assertEquals(fuelTariffId, tariffDetails.getId());
        assertEquals(exchangeRate, tariffDetails.getExchangeRate(), 0);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testUpdateTariffShouldTakeTariffFuelDetailsAndCallUpdateFuelTariffWithIdExchangeRateAndFuelName() throws Exception {
        TariffFuelDetails tariffFuelDetails = new TariffFuelDetails(fuelTariffId, exchangeRate, null);
        mvc.perform(put("/api/tariff/update").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(tariffFuelDetails)))
                .andExpect(status().is(204));
        verify(fuelTariffService).updateFuelTariff(eq(fuelTariffId), eq(exchangeRate), isNull());
    }
}
