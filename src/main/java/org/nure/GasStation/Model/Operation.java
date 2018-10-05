package org.nure.GasStation.Model;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;

public class Operation {
    private final String operationId;
    private final float amount;
    private final float price;
    private final Date date;
    private final String fuelName;
    private final String username;

    public Operation(String operationId, float amount, float price, Date date, String fuelName, String username) {
        this.operationId = operationId;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.fuelName = fuelName;
        this.username = username;
    }

    public String getOperationId() {
        return operationId;
    }

    public float getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public String getFuelName() {
        return fuelName;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, "amount", "price", "date", "fuelName", "username");
    }
}
