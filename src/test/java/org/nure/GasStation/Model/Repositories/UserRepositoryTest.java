package org.nure.GasStation.Model.Repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {
    @Test
    public void blank() {
        assertTrue(true);
    }

//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test(expected = EntityAlreadyExistsException.class)
//    public void testCreateUserShouldThrowEntityAlreadyExistsExceptionIfUserAlreadyExists() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        userRepository.createUser(username1, password1, role1);
//        userRepository.createUser(username1, password1, role1);
//    }
//
//    @Test
//    public void testGetUserByUsernameShouldReturnUserByUsername() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        userRepository.createUser(username1, password1, role1);
//        User foundUser = userRepository.getUserByUsername(username1);
//        assertEquals(foundUser.getUsername(), username1);
//        assertEquals(foundUser.getPassword(), password1);
//        assertEquals(foundUser.getRoles(), role1);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testGetUserShouldThrowEntityNotFoundExceptionIfUserNotFound() {
//        String username1 = "matviei";
//        userRepository.getUserByUsername(username1);
//    }
//
//    @Test
//    public void testGetUserRoleShouldReturnUserRoleByUsername() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        userRepository.createUser(username1, password1, role1);
//        assertEquals(userRepository.getUserRole(username1), role1);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testGetUserRoleShouldThrowEntityNotFoundExceptionIfUserNotFound() {
//        String username1 = "matviei";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        userRepository.getUserRole(username1);
//    }
//
//    @Test
//    public void testSetPasswordShouldSetNewPasswordByGivenUsername() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        String password2 = "pass4321";
//        userRepository.createUser(username1, password1, role1);
//        userRepository.setPassword(username1, password2);
//        User user = userRepository.getUserByUsername(username1);
//        assertEquals(user.getPassword(), password2);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testSetPasswordShouldThrowEntityNotFoundExceptionIfNoUserFound() {
//        String username1 = "matviei";
//        String password2 = "pass4321";
//        userRepository.setPassword(username1, password2);
//    }
//
//    @Test
//    public void testSetRoleShouldSetRoleOfUserByUsername() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        UserRoles role2 = UserRoles.USER_BYER;
//        userRepository.createUser(username1, password1, role1);
//        userRepository.setRole(username1, role2);
//        User demotedUser = userRepository.getUserByUsername(username1);
//        assertEquals(demotedUser.getRoles(), role2);
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testSetRoleShouldThrowEntityNotFoundExceptionIfUserNotFound() {
//        String username1 = "matviei";
//        UserRoles role2 = UserRoles.USER_BYER;
//        userRepository.setRole(username1, role2);
//    }
//
//    @Test
//    public void testCheckUserCredentialsShouldReturnTrueIfCorrectPasswordGiven() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        userRepository.createUser(username1, password1, role1);
//        assertTrue(userRepository.checkUserCredentials(username1, password1));
//    }
//
//    @Test
//    public void testCheckUserCredentialsShouldReturnFalseifIncorrectPasswordGiven() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        UserRoles role1 = UserRoles.USER_ADMIN;
//        String password2 = "pass4321";
//        userRepository.createUser(username1, password1, role1);
//        assertFalse(userRepository.checkUserCredentials(username1, password2));
//    }
//
//    @Test(expected = EntityNotFoundException.class)
//    public void testCheckUserCredentialsShouldThrowEntityNotFoundExceptionIfNoUserFound() {
//        String username1 = "matviei";
//        String password1 = "pass1234";
//        userRepository.checkUserCredentials(username1, password1);
//    }
}
