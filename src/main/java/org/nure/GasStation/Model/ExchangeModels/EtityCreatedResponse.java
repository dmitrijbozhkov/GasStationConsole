package org.nure.GasStation.Model.ExchangeModels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EtityCreatedResponse {
    private String message;
    private String id;
}
