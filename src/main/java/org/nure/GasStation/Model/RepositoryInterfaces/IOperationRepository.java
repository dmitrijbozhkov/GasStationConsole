package org.nure.GasStation.Model.RepositoryInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.NotEnoughPrivilegesException;
import org.nure.GasStation.Model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;

public interface IOperationRepository extends JpaRepository<Operation, Long> { }
