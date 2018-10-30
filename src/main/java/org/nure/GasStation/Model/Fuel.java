package org.nure.GasStation.Model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.nure.GasStation.Exceptions.InputDataValidationException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
public class Fuel {

    private String fuelName;
    private float price;
    private float fuelLeft;
    private float maxFuel;
    private String description;
    private Set<Operation> operations;

    public Fuel() { }

    public Fuel(String fuelName, float price, float fuelLeft, float maxFuel, String description) {
        this.fuelName = fuelName;
        this.price = price;
        this.fuelLeft = fuelLeft;
        this.maxFuel = maxFuel;
        this.description = description;
        this.operations = new HashSet<Operation>();
    }

    public Fuel(String fuelName, float price, float fuelLeft, float maxFuel, String description, HashSet<Operation> operations) {
        this.fuelName = fuelName;
        this.price = price;
        this.fuelLeft = fuelLeft;
        this.maxFuel = maxFuel;
        this.description = description;
        this.operations = operations;
    }

    @Id
    @Column
    public String getFuelName() {
        return this.fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
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

    public void setFuelLeft(float fuelLeft) throws InputDataValidationException {
        if (fuelLeft > maxFuel) {
            throw new InputDataValidationException("Лимит хранилища топлива 10000 литров");
        }
        this.fuelLeft = fuelLeft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getMaxFuel() {
        return maxFuel;
    }

    public void setMaxFuel(float maxFuel) {
        this.maxFuel = maxFuel;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuel")
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
