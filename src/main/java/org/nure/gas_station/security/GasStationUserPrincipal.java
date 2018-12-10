package org.nure.gas_station.Security;

import org.nure.gas_station.model.Enumerations.UserRoles;
import org.nure.gas_station.model.GasStationUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class GasStationUserPrincipal implements UserDetails {

    private final String username;
    private final String password;
    private final UserRoles userAutority;


    public GasStationUserPrincipal(GasStationUser gasStationUser) {
        this.username = gasStationUser.getUsername();
        this.password = gasStationUser.getPassword();
        this.userAutority = gasStationUser.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>(Arrays.asList(this.userAutority));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
