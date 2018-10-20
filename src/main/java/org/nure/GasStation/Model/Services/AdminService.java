package org.nure.GasStation.Model.Services;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return userRepository.findByUsernameContainingIgnoreCase(query, new PageRequest(page, amount));
    }

    @Override
    public void setRole(String username, UserRoles role) throws EntityNotFoundException {
        GasStationUser user = getUser(username);
        user.setRoles(role);
        userRepository.flush();
    }
}
