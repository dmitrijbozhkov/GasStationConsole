package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;

public interface IAdminService {

    void setRole(String username, UserRoles role) throws EntityNotFoundException;
    UserRoles getUserRole(String username) throws EntityNotFoundException;
}
