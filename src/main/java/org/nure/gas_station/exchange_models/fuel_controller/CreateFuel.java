package org.nure.gas_station.exchange_models.fuel_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.Fuel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFuel extends RequestFuel {

    public CreateFuel(Fuel fuel) {
        super(fuel.getFuelName());
        this.tariffId = fuel.getFuelTariff().getId();
        this.fuelLeft = fuel.getFuelStorage().getFuelAmount();
    }

    @NotNull(message = "Please provide fuel tariff")
    @JsonProperty("tariffId")
    private long tariffId;
    @NotNull(message = "Please provide number of fuel left in storage")
    @JsonProperty("fuelLeft")
    private float fuelLeft;
}
