package org.nure.gas_station.exchange_models.admin_controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;

@Data
@NoArgsConstructor
public class UserDetails {

    @JsonProperty("username")
    private String username;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("role")
    private UserRoles role;

    public UserDetails(GasStationUser user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRoles();
    }

    public UserDetails(String username, String name, String surname, UserRoles role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}
