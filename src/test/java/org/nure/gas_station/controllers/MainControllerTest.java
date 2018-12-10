package org.nure.gas_station.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHomePageShouldReturnViewWithNameIndex() throws Exception {
        mvc.perform(get("/")).andExpect(view().name("index"));
    }
}
