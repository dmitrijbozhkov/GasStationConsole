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
    @Id
    private String fuelName;
    @Column(nullable = false)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fuel_storage_id")
    private FuelStorage fuelStorage;
    @Column(nullable = false)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fuel_tariff_id")
    private FuelTariff fuelTariff;
}
