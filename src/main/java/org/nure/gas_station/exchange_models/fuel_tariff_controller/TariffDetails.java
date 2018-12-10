package org.nure.gas_station.exchange_models.tariff_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.FuelTariff;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TariffDetails {

    public TariffDetails(long id, float exchangeRate) {
        this.id = id;
        this.exchangeRate = exchangeRate;
    }

    public TariffDetails(FuelTariff fuelTariff) {
        this.id = fuelTariff.getId();
        this.exchangeRate = fuelTariff.getExchangeRate();
    }

    @NotNull(message = "Please provide an id for tariff")
    @JsonProperty("id")
    private long id;
    @NotNull(message = "Please set exchange rate for tariff")
    @JsonProperty("exchangeRate")
    private float exchangeRate;
}
