package org.nure.GasStation.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.command.ddl.CreateUser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.AuthToken;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.ChangePassword;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.CreateUserCredentials;
import org.nure.GasStation.Model.ExchangeModels.GasStationUserController.UserCredentials;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.nure.GasStation.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GasStationGasStationUserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IUserService userServiceMock;
    @MockBean
    private JwtTokenProvider tokenProvider;
    private static ObjectMapper map;

    // Default user
    private final String username = "matviei";
    private final String password = "pass1234";
    private final String name = "Matthew";
    private final String surname = "Serbull";
    private final UserRoles userRole = UserRoles.ROLE_BYER;

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    @Test
    public void testSigninShouldReturnOk() throws Exception {
        CreateUserCredentials credentials = new CreateUserCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        credentials.setName(name);
        credentials.setSurname(surname);
        mvc.perform(post("/api/user/signin")
                .contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(credentials)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSigninShouldCreateUser() throws Exception {
        CreateUserCredentials credentials = new CreateUserCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        credentials.setName(name);
        credentials.setSurname(surname);
        mvc.perform(post("/api/user/signin")
                .contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(credentials)));
        verify(userServiceMock).createUser(credentials.getUsername(), credentials.getPassword(), credentials.getName(), credentials.getSurname(), UserRoles.ROLE_BYER);
    }

    @Test
    public void testLoginShouldAuthenticateUser() throws Exception {
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        String token = "sdfsdfwernmnlkdmfgkmk324k3j5knklkKNDLKDNFSoiw43j0";
        given(tokenProvider.generateToken(null)).willReturn(token);
        mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(credentials)));
        verify(userServiceMock).authenticate(credentials.getUsername(), credentials.getPassword());
    }

    @Test
    public void testLoginShouldReturnAuthTokenWIthToken() throws Exception {
        UserCredentials credentials = new UserCredentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        String token = "sdfsdfwernmnlkdmfgkmk324k3j5knklkKNDLKDNFSoiw43j0";
        given(tokenProvider.generateToken(null)).willReturn(token);
        MvcResult result = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(credentials)))
                .andExpect(status().isOk())
                .andReturn();
        String tokenObj = result.getResponse().getContentAsString();
        assertEquals(map.writeValueAsString(new AuthToken(token)), tokenObj);
    }

    @Test
    @WithMockUser(username = "matviei", authorities = { "ROLE_ADMIN" })
    public void testChangePasswordShouldCallChangePasswordWithSuppliedData() throws Exception {
        String nextPassword = "newpass";
        ChangePassword changePassword = new ChangePassword(nextPassword, password);
        mvc.perform(post("/api/user/change-password").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(changePassword)))
                .andExpect(status().isOk());
        verify(userServiceMock).changePassword("matviei", nextPassword, password);
    }

}
