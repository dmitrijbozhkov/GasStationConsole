package org.nure.GasStation.Model.ExchangeModels;

import java.io.Serializable;

public class EtityCreatedResponse implements Serializable {
    private String message;
    private String id;

    public EtityCreatedResponse(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(String id) {
        this.id = id;
    }
}
