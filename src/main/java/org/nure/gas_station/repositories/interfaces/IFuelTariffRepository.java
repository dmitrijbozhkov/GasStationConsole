package org.nure.gas_station.repositories.interfaces;

import org.nure.gas_station.model.FuelTariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFuelTariffRepository extends JpaRepository<FuelTariff, Long> { }
