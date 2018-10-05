package org.nure.GasStation.Model.Repositories;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.User;

public interface IUserRepository {
    void createUser(String username, String password, String role) throws EntityAlreadyExistsException;
    User getUserByUsername(String username) throws EntityNotFoundException;
    String getUserRole(String username) throws EntityNotFoundException;
    void setPassword(String username, String password) throws EntityNotFoundException;
    void setRole(String username, String role) throws EntityNotFoundException;
    boolean checkUserCredentials(String username, String password) throws EntityNotFoundException;
}
