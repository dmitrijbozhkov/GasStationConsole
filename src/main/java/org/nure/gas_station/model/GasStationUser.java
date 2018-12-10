package org.nure.gas_station.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.enumerations.UserRoles;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GasStationUser {

    public GasStationUser(String username, String password, String name, String surname, UserRoles roles) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.roles = roles;
        this.userOperations = new HashSet<FuelOrder>();
    }

    @Id
    private String username;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private UserRoles roles;
    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FuelOrder> userOperations;
}
