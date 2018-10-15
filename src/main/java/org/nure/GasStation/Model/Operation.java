package org.nure.GasStation.Model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.nure.GasStation.Model.Enumerations.OperationTypes;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Operation {

    private long operationId;
    private float amount;
    private float price;
    private Date date;
    private Fuel fuel;
    private User user;
    private OperationTypes type;

    public Operation(long operationId, float amount, float price, Date date, Fuel fuel, User user, OperationTypes type) {
        this.operationId = operationId;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.fuel = fuel;
        this.user = user;
        this.type = type;
    }

    @Id
    @Column
    public long getOperationId() {
        return operationId;
    }

    public float getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    @Enumerated(EnumType.ORDINAL)
    public OperationTypes getType() {
        return type;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Fuel getFuel() {
        return fuel;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(OperationTypes type) {
        this.type = type;
    }
}
