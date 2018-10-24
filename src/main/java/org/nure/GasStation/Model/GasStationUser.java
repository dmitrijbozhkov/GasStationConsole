package org.nure.GasStation.Model;

import org.nure.GasStation.Model.Enumerations.UserRoles;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
public class GasStationUser {

    private String username;
    private String name;
    private String surname;
    private String password;
    private UserRoles roles;
    private Set<Operation> userOperations;

    public GasStationUser() { }

    public GasStationUser(String username, String password, String name, String surname, UserRoles roles) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.roles = roles;
        this.userOperations = new HashSet<Operation>();
    }

    public GasStationUser(String username, String password, String name, String surname, UserRoles roles, HashSet<Operation> userOperations) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.roles = roles;
        this.userOperations = userOperations;
    }

    @Id
    @Column
    public String getUsername() {
        return username;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    @Enumerated(EnumType.STRING)
    public UserRoles getRoles() {
        return roles;
    }

    public void setRoles(UserRoles roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gasStationUser")
    public Set<Operation> getUserOperations() {
        return userOperations;
    }

    public void setUserOperations(Set<Operation> userOperations) {
        this.userOperations = userOperations;
    }
}
