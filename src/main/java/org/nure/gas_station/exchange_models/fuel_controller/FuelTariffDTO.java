package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateFuelTariff extends RequestFuel {

    public UpdateFuelTariff(String fuelName, @NotBlank(message = "Tariff id can't be empty") long tariffId) {
        super(fuelName);
        this.tariffId = tariffId;
    }

    @NotNull(message = "Please provide fuel tariff")
    @JsonProperty("tariffId")
    private long tariffId;
}
