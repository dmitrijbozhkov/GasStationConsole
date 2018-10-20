package org.nure.GasStation.Model.ExchangeModels;

import java.io.Serializable;

public class UserCredentials implements Serializable {
    private String username;
    private String password;

    public UserCredentials() { }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
