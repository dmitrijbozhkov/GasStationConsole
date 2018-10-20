package org.nure.GasStation.Model;

import org.nure.GasStation.Model.Enumerations.UserRoles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Access(AccessType.PROPERTY)
public class User {

    private String username;
    private String password;
    private UserRoles roles;
    private Set<Operation> userOperations;

    public User(String username, String password, UserRoles roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.userOperations = new HashSet<Operation>();
    }

    public User(String username, String password, UserRoles roles, HashSet<Operation> userOperations) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.userOperations = userOperations;
    }

    @Id
    @Column
    public String getUsername() {
        return username;
    }

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Operation> getUserOperations() {
        return userOperations;
    }

    public void setUserOperations(Set<Operation> userOperations) {
        this.userOperations = userOperations;
    }
}
