package org.nure.gas_station.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.nure.gas_station.services.implementations.UserService;
import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.InputDataValidationException;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.repositories.interfaces.IUserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationUserServiceTest {

    private IUserRepository userRepository;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private SecurityContextHolder securityContextHolder;

    private final String correctUsername = "matviei";
    private final String correctPassword = "pass1234";
    private final String name = "pepe";
    private final String surname = "kek";
    private final UserRoles correctUserRole = UserRoles.ROLE_BYER;
    private final String incorrectUsername = "pep";
    private final String incorrectPassword = "pass";
    private final String changingPassword = "pass4321";

    @Before
    public void setupRepo() {
        this.authenticationManager = mock(AuthenticationManager.class);
        this.userRepository = mock(IUserRepository.class);
        this.userService = new UserService(this.userRepository, this.authenticationManager, new BCryptPasswordEncoder());
    }

    @Test(expected = InputDataValidationException.class)
    public void testCreateUserSHouldThrowInputDataValidationExceptionIfUsernameIsInvalid() {
        userService.createUser(incorrectUsername, correctPassword, name, surname, correctUserRole);
    }

    @Test(expected = InputDataValidationException.class)
    public void testCreateUserSHouldThrowInputDataValidationExceptionIfPasswordIsInvalid() {
        userService.createUser(correctUsername, incorrectPassword, name, surname, correctUserRole);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testCreateUserShouldThrowEntityAlreadyExistsExceptionIfUserExists() {
        given(userRepository.findById(correctUsername)).willReturn(Optional.of(new GasStationUser(correctUsername, correctPassword, name, surname, correctUserRole)));
        userService.createUser(correctUsername, correctPassword, name, surname, correctUserRole);
    }

    @Test
    public void testCreateUserShouldSaveNewUserIfEverythingCorrect() {
        given(userRepository.findById(correctUsername)).willReturn(Optional.empty());
        userService.createUser(correctUsername, correctPassword, name, surname, correctUserRole);
        verify(userRepository).save(isA(GasStationUser.class));
    }

    @Test
    public void testAuthenticateShouldAuthenticateUserByCredentials() {
        Authentication auth = mock(Authentication.class);
        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(correctUsername, correctPassword))).willReturn(auth);
        Authentication givenAuth = userService.authenticate(correctUsername, correctPassword);
        assertEquals(auth, givenAuth);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testChangePasswordShouldThrowEntityNotFoundExceptionIfUserNotFound() {
        given(userRepository.findById(correctUsername)).willReturn(Optional.empty());
        userService.changePassword(correctUsername, changingPassword, correctPassword);
    }

    @Test(expected = InputDataValidationException.class)
    public void testChangePasswordShouldThrowInputDataValidationExceptionIfOldPasswordIsWrong() {
        GasStationUser user = spy(new GasStationUser(correctUsername, correctPassword, name, surname, correctUserRole));
        given(userRepository.findById(correctUsername)).willReturn(Optional.of(user));
        userService.changePassword(correctUsername, changingPassword, incorrectPassword);
    }

    @Test
    public void testChangePasswordShouldChangePasswordInEntity() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        GasStationUser user = spy(new GasStationUser(correctUsername, encoder.encode(correctPassword), name, surname, correctUserRole));
        given(userRepository.findById(correctUsername)).willReturn(Optional.of(user));
        userService.changePassword(correctUsername, changingPassword, correctPassword);
        verify(user).setPassword(argThat(settingPassword -> encoder.matches(changingPassword, settingPassword)));
        verify(userRepository).flush();
    }
}
