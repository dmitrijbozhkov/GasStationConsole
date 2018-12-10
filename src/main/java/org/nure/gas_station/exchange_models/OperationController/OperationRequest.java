package org.nure.gas_station.exchange_models.OperationController;

import lombok.Value;

@Value
public class OperationRequest {
    private String fuelName;
    private float amount;
}
