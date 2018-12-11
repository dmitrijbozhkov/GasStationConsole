package org.nure.gas_station.repositories.interfaces;

import org.nure.gas_station.model.GasStationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<GasStationUser, String> {
    public Page<GasStationUser> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
