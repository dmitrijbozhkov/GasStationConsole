package org.nure.gas_station.services.interfaces;

import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.springframework.security.core.Authentication;

public interface IUserService {

    void createUser(String username, String password, String name, String surname, UserRoles role) throws EntityAlreadyExistsException;
    void changePassword(String username, String password, String oldPassword) throws EntityNotFoundException, IllegalArgumentException;
    Authentication authenticate(String username, String password);
}
