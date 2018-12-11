package org.nure.gas_station.exchange_models.gas_station_user_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCredentials extends UserCredentials {
    @NotBlank(message = "Name can't be empty")
    @JsonProperty("name")
    private String name;
    @NotBlank(message = "Surname can't be empty")
    @JsonProperty("surname")
    private String surname;
}
