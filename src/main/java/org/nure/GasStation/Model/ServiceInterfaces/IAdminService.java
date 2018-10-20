package org.nure.GasStation.Model.ServiceInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.GasStationUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAdminService {
    GasStationUser getUser(String username) throws EntityNotFoundException;
    Page<GasStationUser> searchForUser(String query, int amount, int page);
    void setRole(String username, UserRoles role) throws EntityNotFoundException;
}
