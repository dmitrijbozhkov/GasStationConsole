package org.nure.GasStation.Model.ExchangeModels.AdminController;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.nure.GasStation.Model.Enumerations.UserRoles;

@Value
public class ChangeUserRole {
    String username;
    UserRoles userRole;
}
