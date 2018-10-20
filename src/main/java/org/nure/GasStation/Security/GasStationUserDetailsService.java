package org.nure.GasStation.Security;

import org.nure.GasStation.Model.GasStationUser;
import org.nure.GasStation.Model.RepositoryInterfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class GasStationUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<GasStationUser> search = userRepository.findById(username);
        if (!search.isPresent()) {
            throw new UsernameNotFoundException(String.format("GasStationUser by username %s not found", username));
        }
        return new GasStationUserPrincipal(search.get());
    }
}
