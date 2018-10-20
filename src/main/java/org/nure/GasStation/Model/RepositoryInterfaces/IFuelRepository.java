package org.nure.GasStation.Model.RepositoryInterfaces;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface IFuelRepository extends JpaRepository<Fuel, String> { }
