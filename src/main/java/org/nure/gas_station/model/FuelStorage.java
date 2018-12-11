package org.nure.gas_station.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nure.gas_station.exceptions.InputDataValidationException;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FuelStorage {

    public FuelStorage(float fuelAmount) {
        this.setFuelAmount(fuelAmount);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Column(nullable = false)
    @Getter
    private float fuelAmount;

    public void setFuelAmount(float fuelAmount) {
        if (fuelAmount > this.maxFuel) {
            throw new InputDataValidationException("Can't store more than 10000 liters of fuel");
        }
        if (fuelAmount < 0) {
            throw new InputDataValidationException("Can't store less than 0 fuel");
        }
        this.fuelAmount = fuelAmount;
    }

    @Getter
    private static final int maxFuel = 10000;
}
