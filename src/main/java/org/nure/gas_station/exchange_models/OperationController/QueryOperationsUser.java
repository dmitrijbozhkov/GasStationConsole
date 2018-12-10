package org.nure.gas_station.exchange_models.OperationController;

import lombok.Data;

@Data
public class QueryOperationsUser extends QueryOperationsPage {
    private String username;
}
