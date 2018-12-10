package org.nure.gas_station.services.implementations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.nure.gas_station.exceptions.EntityAlreadyExistsException;
import org.nure.gas_station.exceptions.EntityNotFoundException;
import org.nure.gas_station.exceptions.InputDataValidationException;
import org.nure.gas_station.model.enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;
import org.nure.gas_station.repositories.interfaces.IUserRepository;
import org.nure.gas_station.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void createUser(String username, String password, String name, String surname, UserRoles role) throws EntityAlreadyExistsException, InputDataValidationException {
        Optional<GasStationUser> search = userRepository.findById(username);
        if (search.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("GasStationUser with name of %s already exists", username));
        }
        userRepository.save(new GasStationUser(username, encoder.encode(password), name, surname, role));
    }

    @Override
    public Authentication authenticate(String username, String password) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    @Override
    public void changePassword(String username, String password, String oldPassword) throws EntityNotFoundException, InputDataValidationException {
        Optional<GasStationUser> search = userRepository.findById(username);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("User %s not found, can't change password", username));
        }
        GasStationUser user = search.get();
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new InputDataValidationException("Old password isn't correct");
        }
        user.setPassword(encoder.encode(password));
        userRepository.flush();
    }
}
