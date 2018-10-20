package org.nure.GasStation.Model.Enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    ROLE_BYER("ROLE_BYER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String authorityName;

    UserRoles(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return this.authorityName;
    }
}
