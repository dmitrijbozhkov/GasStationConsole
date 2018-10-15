package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.User;

public interface IUserService {

    void createUser(String username, String password, UserRoles role) throws EntityAlreadyExistsException;
    User getUserByUsername(String username) throws EntityNotFoundException;
    void setPassword(String username, String password) throws EntityNotFoundException;

}
