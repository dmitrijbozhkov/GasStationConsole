package org.nure.gas_station.exchange_models.gas_station_user_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {
    @NotBlank(message = "Username can't be empty")
    @Size(min = 4, max = 26, message = "Username must be 4 to 26 characters long")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "Username should consist only of latin characters and numbers")
    @JsonProperty("username")
    private String username;
    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 26, message = "Password must be 6 to 26 characters long")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "Password should consist only of latin characters and numbers")
    @JsonProperty("password")
    private String password;
}
