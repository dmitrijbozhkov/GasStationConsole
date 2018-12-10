package org.nure.gas_station.exchange_models.tariff_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffFuelDetails extends TariffDetails {

    @NotBlank(message = "Please set fuel name for tariff")
    @JsonProperty("fuelName")
    private String fuelName;
}
