package org.nure.GasStation.Model;

import org.nure.GasStation.Model.Enumerations.OperationTypes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Operation {

    private long operationId;
    private float amount;
    private float price;
    private Date operationDate;
    private Fuel fuel;
    private GasStationUser gasStationUser;
    private OperationTypes type;

    public Operation() { }

    public Operation(float amount, float price, Date operationDate, Fuel fuel, GasStationUser gasStationUser, OperationTypes type) {
        this.amount = amount;
        this.price = price;
        this.operationDate = operationDate;
        this.fuel = fuel;
        this.gasStationUser = gasStationUser;
        this.type = type;
    }

    public Operation(long operationId, float amount, float price, Date operationDate, Fuel fuel, GasStationUser gasStationUser, OperationTypes type) {
        this.operationId = operationId;
        this.amount = amount;
        this.price = price;
        this.operationDate = operationDate;
        this.fuel = fuel;
        this.gasStationUser = gasStationUser;
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
    public Date getOperationDate() {
        return operationDate;
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
    public GasStationUser getGasStationUser() {
        return gasStationUser;
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

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setGasStationUser(GasStationUser gasStationUser) {
        this.gasStationUser = gasStationUser;
    }

    public void setType(OperationTypes type) {
        this.type = type;
    }
}
