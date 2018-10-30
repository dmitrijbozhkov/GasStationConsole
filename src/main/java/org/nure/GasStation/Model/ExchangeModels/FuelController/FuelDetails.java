package org.nure.GasStation.Model.ExchangeModels.FuelController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.nure.GasStation.Model.Enumerations.UserRoles;

@Value
public class FuelDetails {
    private String fuelName;
    private float price;
    private float fuelLeft;
    private float maxFuel;
    private String description;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public FuelDetails(
            @JsonProperty("fuelName") String fuelName,
            @JsonProperty("price") float price,
            @JsonProperty("fuelLeft") float fuelLeft,
            @JsonProperty("maxFuel") float maxFuel,
            @JsonProperty("description") String description) {
        this.fuelName = fuelName;
        this.price = price;
        this.fuelLeft = fuelLeft;
        this.maxFuel = maxFuel;
        this.description = description;
    }
}
