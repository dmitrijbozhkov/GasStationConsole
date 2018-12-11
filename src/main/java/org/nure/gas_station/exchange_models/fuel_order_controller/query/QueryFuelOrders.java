package org.nure.gas_station.exchange_models.fuel_order_controller.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class QueryFuelOrders extends QueryOrders {

    public QueryFuelOrders(int page, int amount, String fuelName) {
        super(page, amount);
        this.fuelName = fuelName;
    }

    @JsonProperty("fuelName")
    @NotBlank(message = "Please provide fuel name")
    private String fuelName;
}
