package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.FuelStorage;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelStorageDetails {

    public FuelStorageDetails(FuelStorage fuelStorage) {
        this.id = fuelStorage.getId();
        this.fuelAmount = fuelStorage.getFuelAmount();
    }

    @JsonProperty("id")
    @NotNull(message = "Please provide id for fuel storage")
    private long id;
    @JsonProperty("fuelAmount")
    @NotNull(message = "Please set fuel amount of fuel storage")
    private float fuelAmount;
}
