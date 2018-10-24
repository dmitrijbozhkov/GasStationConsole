package org.nure.GasStation.Model.ExchangeModels.FuelController;

import lombok.Value;

@Value
public class CreateFuel {
    private String fuelName;
    private float price;
    private float fuelLeft;
}
