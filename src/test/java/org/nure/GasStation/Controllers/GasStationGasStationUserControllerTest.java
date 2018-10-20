package org.nure.GasStation.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.ExchangeModels.AuthToken;
import org.nure.GasStation.Model.ExchangeModels.UserCredentials;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.nure.GasStation.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GasStationUserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IUserService userServiceMock;
    @MockBean
    private JwtTokenProvider tokenProvider;
    private static ObjectMapper map;
    private final UserCredentials user = new UserCredentials("name", "pass");

    @BeforeClass
    public static void setup() {
        map = new ObjectMapper();
    }

    @Test
    public void testSigninShouldReturnOk() throws Exception {
        mvc.perform(post("/api/user/signin")
                .contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSigninShouldCreateUser() throws Exception {
        mvc.perform(post("/api/user/signin")
                .contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(user)));
        verify(userServiceMock).createUser(user.getUsername(), user.getPassword(), UserRoles.ROLE_BYER);
    }

    @Test
    public void testLoginShouldAuthenticateUser() throws Exception {
        String token = "sdfsdfwernmnlkdmfgkmk324k3j5knklkKNDLKDNFSoiw43j0";
        given(tokenProvider.generateToken(null)).willReturn(token);
        mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(user)));
        verify(userServiceMock).authenticate(user.getUsername(), user.getPassword());
    }

    @Test
    public void testLoginShouldReturnAuthTokenWIthToken() throws Exception {
        String token = "sdfsdfwernmnlkdmfgkmk324k3j5knklkKNDLKDNFSoiw43j0";
        given(tokenProvider.generateToken(null)).willReturn(token);
        MvcResult result = mvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON).content(map.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        String tokenObj = result.getResponse().getContentAsString();
        assertEquals(map.writeValueAsString(new AuthToken(token)), tokenObj);
    }

}
