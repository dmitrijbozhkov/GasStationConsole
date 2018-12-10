package org.nure.gas_station.exchange_models.ExceptionController;

import lombok.Value;

@Value
public class ExceptionResponse {
    private String exceptionType;
    private String exceptionMessage;
}
