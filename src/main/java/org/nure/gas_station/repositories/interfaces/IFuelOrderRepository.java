package org.nure.gas_station.repositories.interfaces;

import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.GasStationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import java.util.Date;

public interface IFuelOrderRepository extends JpaRepository<FuelOrder, Long> {
    Page<FuelOrder> findAllByGasStationUser(GasStationUser gasStationUser, Pageable pageable);
    Page<FuelOrder> findAllByOrderDateBetween(
            @Temporal(TemporalType.TIMESTAMP) Date orderDateStart,
            @Temporal(TemporalType.TIMESTAMP) Date orderDateEnd,
            Pageable pageable);
    Page<FuelOrder> findAllByFuelAndOrderDateBetween(
            Fuel fuel,
            @Temporal(TemporalType.TIMESTAMP) Date operationDateStart,
            @Temporal(TemporalType.TIMESTAMP) Date operationDateEnd,
            Pageable pageable);
}
