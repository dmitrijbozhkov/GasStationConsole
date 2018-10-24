package org.nure.GasStation.Model.ExchangeModels.OperationController;

import lombok.Data;

import java.util.Date;

@Data
public class QueryOperationsDate extends QueryOperationsPage {
    private Date before;
    private Date after;
}
