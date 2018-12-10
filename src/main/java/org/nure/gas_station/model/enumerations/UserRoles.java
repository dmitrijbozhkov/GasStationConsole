package org.nure.gas_station.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority {
    ROLE_BUYER("ROLE_BUYER"),
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
