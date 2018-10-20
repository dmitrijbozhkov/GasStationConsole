package org.nure.GasStation.Model.Services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserSHouldThrowIllegalArgumentExceptionIfUsernameIsInvalid() {
        userService.createUser("pepe", "pass1234", UserRoles.ROLE_BYER);
    }
}
