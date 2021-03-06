package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ChangeFuelName extends RequestFuel {

    public ChangeFuelName(String fuelName, String nextFuelName) {
        super(fuelName);
        this.nextFuelName = nextFuelName;
    }

    @NotBlank(message = "Please provide next fuel name")
    @JsonProperty("nextFuelName")
    private String nextFuelName;
}
