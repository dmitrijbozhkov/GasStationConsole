package org.nure.gas_station.exchange_models.OperationController;

import lombok.Data;

import java.util.Date;

@Data
public class QueryOperationsUserDate extends QueryOperationsPage {
    private String username;
    private Date before;
    private Date after;
}
