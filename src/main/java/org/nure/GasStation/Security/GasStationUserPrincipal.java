package org.nure.GasStation.Security;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.nure.GasStation.Model.Enumerations.UserRoles;
import org.nure.GasStation.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GasStationUserPrincipal implements UserDetails {

    private final String username;
    private final String password;
    private final UserRoles userAutority;


    public GasStationUserPrincipal(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userAutority = user.getRoles();
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
