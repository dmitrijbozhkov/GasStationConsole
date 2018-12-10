package org.nure.gas_station.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.exchange_models.AdminController.ChangeUserRole;
import org.nure.gas_station.exchange_models.AdminController.SearchUser;
import org.nure.gas_station.exchange_models.AdminController.UserDetails;
import org.nure.gas_station.exchange_models.PageDTO;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.services.interfaces.IAdminService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IAdminService adminService;

    private static ObjectMapper map;

    // Default user
    private final String username = "matviei";
    private final String password = "pass1234";
    private final String name = "Matthew";
    private final String surname = "Serbull";
    private final UserRoles userRole = UserRoles.ROLE_ADMIN;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

//    @Test
//    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
//    public void testGetUserShouldReturnFoundUserByUsername() throws Exception {
//        GetUser gettingUser = new GetUser(username);
//        GasStationUser user = new GasStationUser(username, password, userRole);
//        given(adminService.getUser(username)).willReturn(user);
//        MvcResult result = mvc.perform(post("/api/admin/get-user").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(gettingUser)))
//                .andExpect(status().isOk())
//                .andReturn();
//        GasStationUser foundUser = map.readValue(result.getResponse().getContentAsString(), GasStationUser.class);
//        assertTrue(foundUser.getUsername().equals(user.getUsername()) && foundUser.getPassword().equals(user.getPassword()) && foundUser.getRoles().equals(user.getRoles()));
//    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testSearchUserShouldReturnFoundUsersByUsername() throws Exception {
        int amount = 5;
        int page = 0;
        String username2 = "supertv";
        String password2 = "pass4321";
        String name2 = "pepe";
        String surname2 = "kek";
        UserRoles userRoles2 = UserRoles.ROLE_BYER;
        String searchQuery = "tv";
        SearchUser searchingUser = new SearchUser(searchQuery, page, amount);
        GasStationUser user1 = new GasStationUser(username, password, name, surname, userRole);
        GasStationUser user2 = new GasStationUser(username2, password2, name2, surname2, userRoles2);
        List<GasStationUser> usersearchedList = Arrays.asList(user1, user2);
        Page<GasStationUser> userPage = new PageImpl<GasStationUser>(usersearchedList, new PageRequest(page, amount), usersearchedList.size());
        given(adminService.searchForUser(searchQuery, amount, page)).willReturn(userPage);
        MvcResult result = mvc.perform(post("/api/admin/search-user").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(searchingUser)))
                .andExpect(status().isOk())
                .andReturn();
        PageDTO<UserDetails> foundUsers = map.readValue(result.getResponse().getContentAsString(), new TypeReference<PageDTO<UserDetails>>() {});
        System.out.println(foundUsers.getContent());
        Optional<UserDetails> u1 = foundUsers.getContent()
                .stream()
                .filter(d -> {
                    return d.getName().equals(name) && d.getRole().equals(userRole) && d.getSurname().equals(surname) && d.getUsername().equals(username);
                })
                .findFirst();
        Optional<UserDetails> u2 = foundUsers.getContent()
                .stream()
                .filter(d -> {
                    return d.getName().equals(name2) && d.getRole().equals(userRoles2) && d.getSurname().equals(surname2) && d.getUsername().equals(username2);
                })
                .findFirst();
        assertTrue(u1.isPresent() && u2.isPresent());
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testSetRoleShouldSetUserRoleToGivenRole() throws Exception {
        UserRoles roleToChange = UserRoles.ROLE_ADMIN;
        ChangeUserRole role = new ChangeUserRole(username, roleToChange);
        mvc.perform(post("/api/admin/set-role").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(role)))
                .andExpect(status().isOk());
        verify(adminService).setRole(username, roleToChange);
    }
}
