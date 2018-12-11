package org.nure.gas_station.exchange_models.fuel_tariff_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.FuelTariff;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TariffFuelDetails extends TariffDetails {

    public TariffFuelDetails(FuelTariff fuelTariff) {
        super(fuelTariff);
        if (fuelTariff.getFuel() != null) {
            this.fuelName = fuelTariff.getFuel().getFuelName();
        }
    }

    public TariffFuelDetails(long id, float exchangeRate, String fuelName) {
        super(id, exchangeRate);
        this.fuelName = fuelName;
    }

    @JsonProperty("fuelName")
    private String fuelName;
}
