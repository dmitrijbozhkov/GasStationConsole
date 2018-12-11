package org.nure.gas_station.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.exceptions.InputDataValidationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fuel {

    public Fuel(String fuelName, FuelStorage fuelStorage, FuelTariff fuelTariff) {
        this.fuelName = fuelName;
        this.fuelStorage = fuelStorage;
        this.fuelTariff = fuelTariff;
    }

    @Id
    private String fuelName;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fuel_storage_id", nullable = false)
    private FuelStorage fuelStorage;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fuel_tariff_id", nullable = false)
    private FuelTariff fuelTariff;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fuel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FuelOrder> fuelOrders = new HashSet<>();
}
