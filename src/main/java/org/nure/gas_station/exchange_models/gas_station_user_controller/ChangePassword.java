package org.nure.gas_station.exchange_models.GasStationUserController;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
public class ChangePassword {
    @NotBlank(message = "Password can't be empty")
    @Size(min = 6, max = 26, message = "Password must be 6 to 26 characters long")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "Password should consist only of latin characters and numbers")
    private String password;
    private String oldPassword;
}
