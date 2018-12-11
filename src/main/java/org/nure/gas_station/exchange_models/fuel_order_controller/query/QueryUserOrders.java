package org.nure.gas_station.exchange_models.fuel_order_controller.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class QueryUserOrders extends QueryOrders {

    public QueryUserOrders(int page, int amount, String username) {
        super(page, amount);
        this.username = username;
    }

    @JsonProperty("username")
    @NotBlank(message = "Please provide username")
    private String username;
}
