package org.nure.GasStation.Model.Services;

import org.nure.GasStation.Exceptions.EntityAlreadyExistsException;
import org.nure.GasStation.Exceptions.EntityNotFoundException;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.nure.GasStation.Model.ServiceInterfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserService() { }

    public UserService(IUserRepository repo, AuthenticationManager manager, BCryptPasswordEncoder encoder) {
        this.userRepository = repo;
        this.authManager = manager;
        this.encoder = encoder;
    }

    private void validateUsername(String username) throws IllegalArgumentException {
        if (username.length() < 4) {
            throw new IllegalArgumentException("Username must be at least 4 characters long");
        }
        if (username.length() > 26) {
            throw new IllegalArgumentException("Username can have maximum 25 characters");
        }
        if (!username.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("Username must contain only latin characters and numbers");
        }
    }

    private void validatePassword(String password) throws IllegalArgumentException {
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        if (password.length() > 26) {
            throw new IllegalArgumentException("Password can have maximum 25 characters");
        }
        if (!password.matches("[A-Za-z0-9]+")) {
            throw new IllegalArgumentException("Password must contain only latin characters and numbers");
        }
    }

    @Override
    public void createUser(String username, String password, UserRoles role) throws EntityAlreadyExistsException, IllegalArgumentException {
        validateUsername(username);
        validatePassword(password);
        Optional<GasStationUser> search = userRepository.findById(username);
        if (search.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("GasStationUser with name of %s already exists", username));
        }
        userRepository.save(new GasStationUser(username, encoder.encode(password), role));
    }

    @Override
    public Authentication authenticate(String username, String password) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    @Override
    public void changePassword(String username, String password, String oldPassword) throws EntityNotFoundException, IllegalArgumentException {
        Optional<GasStationUser> search = userRepository.findById(username);
        if (!search.isPresent()) {
            throw new EntityNotFoundException(String.format("User %s not found, can't change password", username));
        }
        GasStationUser user = search.get();
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password isn't correct");
        }
        user.setPassword(encoder.encode(password));
        userRepository.flush();
    }
}
