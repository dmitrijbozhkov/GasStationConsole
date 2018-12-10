package org.nure.gas_station.exchange_models.OperationController;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Date;

@Value
public class OperationResponse {
    private long operationId;
    private float amount;
    private float price;
    private Date operationDate;
    private String fuelName;
    private String username;
//    private OperationTypes type;
//
//    public OperationResponse(Operation operation) {
//        this.operationId = operation.getOperationId();
//        this.amount = operation.getAmount();
//        this.price = operation.getPrice();
//        this.operationDate = operation.getOperationDate();
//        this.fuelName = operation.getFuel().getFuelName();
//        this.username = operation.getGasStationUser().getUsername();
//        this.type = operation.getType();
//    }
//
//    public OperationResponse(
//            @JsonProperty("operationId") long operationId,
//            @JsonProperty("amount") float amount,
//            @JsonProperty("price") float price,
//            @JsonProperty("operationDate") Date operationDate,
//            @JsonProperty("fuelName") String fuelName,
//            @JsonProperty("username") String username,
//            @JsonProperty("type") OperationTypes type) {
//        this.operationId = operationId;
//        this.amount = amount;
//        this.price = price;
//        this.operationDate = operationDate;
//        this.fuelName = fuelName;
//        this.username = username;
//        this.type = type;
//    }
}
