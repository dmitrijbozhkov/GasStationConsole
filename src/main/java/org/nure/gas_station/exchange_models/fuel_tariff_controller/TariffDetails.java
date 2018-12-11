package org.nure.gas_station.exchange_models.fuel_tariff_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.FuelTariff;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TariffDetails extends CreateTariff {

    public TariffDetails(long id, float exchangeRate) {
        super(exchangeRate);
        this.id = id;
    }

    public TariffDetails(FuelTariff fuelTariff) {
        super(fuelTariff.getExchangeRate());
        this.id = fuelTariff.getId();
    }

    @NotNull(message = "Please provide an id for tariff")
    @JsonProperty("id")
    private long id;
}
