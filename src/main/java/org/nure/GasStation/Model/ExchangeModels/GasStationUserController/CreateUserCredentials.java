package org.nure.GasStation.Model.ExchangeModels.GasStationUserController;

import lombok.Data;

@Data
public class CreateUserCredentials extends UserCredentials {
    private String name;
    private String surname;
}
