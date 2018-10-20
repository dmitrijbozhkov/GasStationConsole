package org.nure.GasStation.Model.RepositoryInterfaces;

import org.nure.GasStation.Model.GasStationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<GasStationUser, String> {
    public Page<GasStationUser> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
