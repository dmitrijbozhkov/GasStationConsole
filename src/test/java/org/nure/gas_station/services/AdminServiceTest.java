package org.nure.gas_station.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.repositories.interfaces.IUserRepository;
import org.nure.gas_station.services.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceTest {

    @MockBean
    private IUserRepository userRepository;
    @Autowired
    private IAdminService adminService;

    // Default user
    private final String username = "matviei";
    private final String password = "pass1234";
    private final String name = "pepe";
    private final String surname = "keke";
    private final UserRoles userRole = UserRoles.ROLE_ADMIN;

    @Test(expected = EntityNotFoundException.class)
    public void testGetUserShouldThrowEntityNotFoundExceptionIfUserNotFound() {
        given(userRepository.findById(username)).willReturn(Optional.empty());
        adminService.getUser(username);
    }

    @Test
    public void testGetUserShouldReturnUserByUsername() {
        GasStationUser user = new GasStationUser(username, password, name, surname, userRole);
        given(userRepository.findById(username)).willReturn(Optional.of(user));
        GasStationUser foundUser = adminService.getUser(username);
        assertEquals(user, foundUser);
    }

    @Test
    public void testSearchForUserShouldLookupUsernamesThatContainQueryIgnoringCase() {
        String searchQuery = "query";
        int amount = 5;
        int page = 1;
        adminService.searchForUser(searchQuery, amount, page);
        verify(userRepository).findByUsernameContainingIgnoreCase(eq(searchQuery), argThat(p -> {
            return p.getPageNumber() == page && p.getPageSize() == amount;
        }));
    }

    @Test
    public void testSetRoleShouldSetUserRole() {
        UserRoles nextRole = UserRoles.ROLE_ADMIN;
        GasStationUser user = new GasStationUser(username, password, name, surname, userRole);
        given(userRepository.findById(username)).willReturn(Optional.of(user));
        adminService.setRole(username, nextRole);
        assertEquals(nextRole, user.getRoles());
    }
}
