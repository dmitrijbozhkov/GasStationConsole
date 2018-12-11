package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.TariffDetails;
import org.nure.gas_station.model.Fuel;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelDetails extends RequestFuel {

    public FuelDetails(Fuel fuel) {
        super(fuel.getFuelName());
        this.tariff = new TariffDetails(fuel.getFuelTariff());
        this.storage = new FuelStorageDetails(fuel.getFuelStorage());
    }

    @NotNull(message = "Please provide exchange tariff for fuel")
    @JsonProperty("tariff")
    private TariffDetails tariff;
    @NotNull(message = "Please provide fuel storage")
    @JsonProperty("storage")
    private FuelStorageDetails storage;
}
