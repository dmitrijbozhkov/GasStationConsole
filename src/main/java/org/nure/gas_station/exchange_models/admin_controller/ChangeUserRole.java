package org.nure.gas_station.exchange_models.AdminController;

import lombok.Value;
import org.nure.gas_station.model.enumerations.UserRoles;

@Value
public class ChangeUserRole {
    String username;
    UserRoles userRole;
}
