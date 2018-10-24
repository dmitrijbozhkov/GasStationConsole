package org.nure.GasStation.Model.ExchangeModels.ExceptionController;

import lombok.Value;

@Value
public class ExceptionResponse {
    private String exceptionType;
    private String exceptionMessage;
}
