package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FuelAmountDTO extends RequestFuel {

    public FuelAmountDTO(String fuelName, float fuelLeft) {
        super(fuelName);
        this.fuelLeft = fuelLeft;
    }

    @NotNull(message = "Please provide number of fuel left in storage")
    @JsonProperty("fuelLeft")
    private float fuelLeft;
}
