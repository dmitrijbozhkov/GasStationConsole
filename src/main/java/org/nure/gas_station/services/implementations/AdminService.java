package org.nure.gas_station.services.implementations;

import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.repositories.interfaces.IUserRepository;
import org.nure.gas_station.services.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public GasStationUser getUser(String username) throws EntityNotFoundException {
        Optional<GasStationUser> search = userRepository.findById(username);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("GasStationUser with name of %s doesn't exists", username));
        }
        return search.get();
    }

    @Override
    public Page<GasStationUser> searchForUser(String query, int amount, int page) {
        return userRepository.findByUsernameContainingIgnoreCase(query, PageRequest.of(page, amount));
    }

    @Override
    @Transactional
    public void setRole(String username, UserRoles role) throws EntityNotFoundException {
        GasStationUser user = getUser(username);
        user.setRoles(role);
    }
}
