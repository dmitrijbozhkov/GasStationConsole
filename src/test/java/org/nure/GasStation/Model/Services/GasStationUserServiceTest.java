package org.nure.GasStation.Model.Services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.InputDataValidationException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
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
