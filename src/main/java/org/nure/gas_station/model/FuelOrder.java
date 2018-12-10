package org.nure.gas_station.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nure.gas_station.model.enumerations.OrderType;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FuelOrder {

    public FuelOrder(float amount, OrderType orderType, Date orderDate, Fuel fuel, FuelTariff fuelTariff, GasStationUser gasStationUser) {
        this.amount = amount;
        this.orderType = orderType;
        this.orderDate = orderDate;
        this.fuel = fuel;
        this.fuelTariff = fuelTariff;
        this.gasStationUser = gasStationUser;
    }

    @Id
    @GeneratedValue
    @Getter
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private float amount;
    @Getter
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;
    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fuel_id", nullable = false)
    private Fuel fuel;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fuel_tariff_id", nullable = false)
    private FuelTariff fuelTariff;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private GasStationUser gasStationUser;

    @Getter
    private static final int maxOrderVolume = 100;
}
