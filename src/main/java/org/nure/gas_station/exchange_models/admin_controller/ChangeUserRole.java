package org.nure.gas_station.exchange_models.admin_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.nure.gas_station.model.enumerations.UserRoles;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserRole {
    @JsonProperty("username")
    @NotBlank(message = "Username cannot be empty")
    String username;
    @JsonProperty("userRole")
    @NotNull(message = "User role cannot be empty")
    UserRoles userRole;
}
