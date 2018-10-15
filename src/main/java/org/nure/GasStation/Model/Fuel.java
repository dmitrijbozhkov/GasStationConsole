package org.nure.GasStation.Model;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
public class Fuel {

    private String fuelName;
    private float price;
    private float fuelLeft;
    private Set<Operation> operations;

    public Fuel(String fuelName, float price, float fuelLeft) {
        this.fuelName = fuelName;
        this.price = price;
        this.fuelLeft = fuelLeft;
        this.operations = new HashSet<Operation>();
    }

    public Fuel(String fuelName, float price, float fuelLeft, HashSet<Operation> operations) {
        this.fuelName = fuelName;
        this.price = price;
        this.fuelLeft = fuelLeft;
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

    public void setFuelLeft(float fuelLeft) throws IllegalArgumentException {
        if (fuelLeft > 10000) {
            throw new IllegalArgumentException("Лимит хранилища топлива 10000 литров");
        }
        this.fuelLeft = fuelLeft;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fuel")
    public Set<Operation> getOperations() {
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
}
