package org.nure.GasStation.Model.ExchangeModels.OperationController;

import lombok.Value;

@Value
public class OperationRequest {
    private String fuelName;
    private float amount;
}
