package org.nure.GasStation.Model.ExchangeModels.AdminController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.GasStationUser;

@Value
public class UserDetails {
    private String username;
    private String name;
    private String surname;
    private UserRoles role;

    public UserDetails(GasStationUser user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRoles();
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDetails(
            @JsonProperty("username") String username,
            @JsonProperty("name")String name,
            @JsonProperty("surname") String surname,
            @JsonProperty("role") UserRoles role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}
