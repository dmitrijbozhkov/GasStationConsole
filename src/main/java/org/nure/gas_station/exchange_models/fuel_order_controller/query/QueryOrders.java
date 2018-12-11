package org.nure.gas_station.exchange_models.fuel_order_controller.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrders {

    @JsonProperty("page")
    @NotNull(message = "Please provide page for query")
    @Min(message = "Page can't be less than 0", value = 0)
    private int page;
    @JsonProperty("amount")
    @NotNull(message = "Please provide amount for query")
    @Min(message = "Amount can't be less than 1", value = 1)
    private int amount;
}
