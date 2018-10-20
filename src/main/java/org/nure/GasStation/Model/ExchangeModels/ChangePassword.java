package org.nure.GasStation.Model.ExchangeModels;

public class ChangePassword {
    private String password;
    private String oldPassword;

    public ChangePassword() { }

    public ChangePassword(String password, String oldPassword) {
        this.password = password;
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
