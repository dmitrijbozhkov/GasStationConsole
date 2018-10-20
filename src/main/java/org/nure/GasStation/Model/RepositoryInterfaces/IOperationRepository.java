package org.nure.GasStation.Model.RepositoryInterfaces;

import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Exceptions.NotEnoughPrivilegesException;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;

public interface IOperationRepository extends JpaRepository<Operation, Long> {
    Page<Operation> findAllByGasStationUser(GasStationUser gasStationUser, Pageable pageable);
    Page<Operation> findAllByOperationDateBetween(
            @Temporal(TemporalType.TIMESTAMP) Date operationDateStart,
            @Temporal(TemporalType.TIMESTAMP) Date operationDateEnd,
            Pageable pageable);
    Page<Operation> findAllByGasStationUserAndOperationDateBetween(
            GasStationUser gasStationUser,
            @Temporal(TemporalType.TIMESTAMP) Date operationDateStart,
            @Temporal(TemporalType.TIMESTAMP) Date operationDateEnd,
            Pageable pageable);
}
