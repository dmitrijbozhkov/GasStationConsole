package org.nure.GasStation.Model;

import org.nure.GasStation.Model.Enumerations.UserRoles;

public class User {
    private final String username;
    private String password;
    private UserRoles roles;

    public User(String username, String password, UserRoles roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRoles getRoles() {
        return roles;
    }

    public void setRoles(UserRoles roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
