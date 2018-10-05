package org.nure.GasStation.Model;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Fuel {
    private final String fuelName;
    private float price;
    private float fuelLeft;

    public Fuel(String fuelName, float price, float fuelLeft) {
        this.fuelName = fuelName;
        this.price = price;
        this.fuelLeft = fuelLeft;
    }

    public String getFuelName() {
        return this.fuelName;
    }

    public float getPrice() {
        return this.price;
    }

    public float getFuelLeft() {
        return this.fuelLeft;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setFuelLeft(float fuelLeft) throws IllegalArgumentException {
        if (fuelLeft > 10000) {
            throw new IllegalArgumentException("Лимит хранилища топлива 10000 литров");
        }
        this.fuelLeft = fuelLeft;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }
}
