package org.nure.gas_station.exchange_models.GasStationUserController;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUserCredentials extends UserCredentials {
    @NotBlank(message = "Name can't be empty")
    private String name;
    @NotBlank(message = "Surname can't be empty")
    private String surname;
}
