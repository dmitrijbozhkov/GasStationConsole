package org.nure.GasStation.Model.ExchangeModels.GasStationUserController;

import lombok.Value;

@Value
public class ChangePassword {
    private String password;
    private String oldPassword;
}
