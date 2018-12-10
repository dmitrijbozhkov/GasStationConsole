package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.Fuel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateFuel extends FuelTariffDTO {

    public CreateFuel(Fuel fuel) {
        super(fuel.getFuelName(), fuel.getFuelTariff().getId());
        this.fuelLeft = fuel.getFuelStorage().getFuelAmount();
    }

    public CreateFuel(String fuelName, long tariffId, float fuelLeft) {
        super(fuelName, tariffId);
        this.fuelLeft = fuelLeft;
    }

    @NotNull(message = "Please provide number of fuel left in storage")
    @JsonProperty("fuelLeft")
    private float fuelLeft;
}
