package org.nure.gas_station.exchange_models.fuel_order_controller.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
public class QueryFuelDateOrders extends QueryDateOrders {

    public QueryFuelDateOrders(int page, int amount, Date before, Date after, String fuelName) {
        super(page, amount, before, after);
        this.fuelName = fuelName;
    }

    @JsonProperty("fuelName")
    @NotBlank(message = "Please provide fuel name")
    private String fuelName;
}
