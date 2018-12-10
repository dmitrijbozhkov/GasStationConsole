package org.nure.gas_station.services.interfaces;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;
import org.springframework.data.domain.Page;

public interface IAdminService {
    GasStationUser getUser(String username) throws EntityNotFoundException;
    Page<GasStationUser> searchForUser(String query, int amount, int page);
    void setRole(String username, UserRoles role) throws EntityNotFoundException;
}
