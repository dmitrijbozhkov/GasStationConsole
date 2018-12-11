package org.nure.gas_station.repositories.interfaces;

import org.nure.gas_station.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFuelRepository extends JpaRepository<Fuel, String> { }
