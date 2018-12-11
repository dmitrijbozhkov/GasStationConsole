package org.nure.gas_station.exchange_models.fuel_tariff_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTariff {

    @NotNull(message = "Please set exchange rate for tariff")
    @JsonProperty("exchangeRate")
    private float exchangeRate;
}
