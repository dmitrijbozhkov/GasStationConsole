package org.nure.gas_station.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exchange_models.fuel_controller.CreateFuel;
import org.nure.gas_station.exchange_models.fuel_controller.RequestFuel;
import org.nure.gas_station.model.Fuel;
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

import java.util.ArrayList;
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
    private final float price = (float) 9.99;
    private final float fuelLeft = 10000;
    private final float maxFuel = 50000;
    private final String description = "Super fuel";
    private static ObjectMapper map;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testAddFuelShouldAddNewFuelAndReturnOkResponse() throws Exception {
        CreateFuel fuel = new CreateFuel(fuelName, price, fuelLeft, maxFuel, description);
        System.out.println(map.writeValueAsString(fuel));
        mvc.perform(post("/api/fuel/add").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(fuel)))
                .andExpect(status().isOk());
        verify(fuelService).addFuel(fuelName, price, fuelLeft, maxFuel, description);
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
        Fuel fuel = new Fuel(fuelName, price, fuelLeft, maxFuel, description);
        given(fuelService.getFuel(fuelName)).willReturn(fuel);
        MvcResult result = mvc.perform(post("/api/fuel/get").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(fuelRequest)))
                .andExpect(status().isOk())
                .andReturn();
        CreateFuel foundFuel = map.readValue(result.getResponse().getContentAsString(), CreateFuel.class);
        assertTrue(foundFuel.getFuelName().equals(fuelName) && foundFuel.getPrice() == price && foundFuel.getFuelLeft() == fuelLeft && foundFuel.getMaxFuel() == maxFuel && foundFuel.getDescription().equals(description));
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testGetFuelsShouldReturnAllFuels() throws Exception {
        String fuelName2 = "92";
        float price2 = (float) 9.88;
        float fuelLeft2 = (float) 3000;
        float maxFuel2 = 50000;
        String description2 = "Super duper fuel";
        Fuel fuel1 = new Fuel(fuelName, price, fuelLeft, maxFuel, description);
        Fuel fuel2 = new Fuel(fuelName2, price2, fuelLeft2, maxFuel2, description2);
        given(fuelService.getFuels()).willReturn(new ArrayList<Fuel>() {{add(fuel1); add(fuel2);}});
        MvcResult result = mvc.perform(get("/api/fuel/get-all"))
                .andExpect(status().isOk())
                .andReturn();
        FuelList foundFuels = map.readValue(result.getResponse().getContentAsString(), FuelList.class);
        Optional<CreateFuel> f1 = foundFuels.getFuels()
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName) && f.getPrice() == price && f.getFuelLeft() == fuelLeft && f.getMaxFuel() == maxFuel && f.getDescription().equals(description))
                .findFirst();
        Optional<CreateFuel> f2 = foundFuels.getFuels()
                .stream()
                .filter(f -> f.getFuelName().equals(fuelName2) && f.getPrice() == price2 && f.getFuelLeft() == fuelLeft2 && f.getMaxFuel() == maxFuel2 && f.getDescription().equals(description2))
                .findFirst();
        assertTrue(f1.isPresent() && f2.isPresent());
    }
}
