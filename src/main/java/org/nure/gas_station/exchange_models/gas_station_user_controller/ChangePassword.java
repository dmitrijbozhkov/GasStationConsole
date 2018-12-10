package org.nure.gas_station.exchange_models.gas_station_user_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
    @NotBlank(message = "Password can't be empty")
    @JsonProperty("password")
    private String password;
    @NotBlank(message = "New password can't be empty")
    @Size(min = 6, max = 26, message = "New password must be 6 to 26 characters long")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "New password should consist only of latin characters and numbers")
    @JsonProperty("oldPassword")
    private String oldPassword;
}
