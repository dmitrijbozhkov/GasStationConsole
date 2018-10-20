package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.springframework.security.core.Authentication;

public interface IUserService {

    void createUser(String username, String password, UserRoles role) throws EntityAlreadyExistsException;
    void changePassword(String username, String password, String oldPassword) throws EntityNotFoundException, IllegalArgumentException;
    Authentication authenticate(String username, String password);
}
