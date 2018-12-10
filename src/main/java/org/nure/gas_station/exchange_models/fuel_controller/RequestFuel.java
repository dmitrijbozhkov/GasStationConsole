package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RequestFuel {

    public RequestFuel(String fuelName) {
        this.fuelName = fuelName;
    }

    @NotBlank(message = "Please provide fuel name")
    @JsonProperty("fuelName")
    private String fuelName;
}
