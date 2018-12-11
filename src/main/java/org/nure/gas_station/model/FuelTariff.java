package org.nure.gas_station.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class FuelTariff {

    public FuelTariff(float exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public FuelTariff(Long id, float exchangeRate) {
        this.id = id;
        this.exchangeRate = exchangeRate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private float exchangeRate;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "fuelTariff")
    @Getter
    @Setter
    private Fuel fuel;
}
