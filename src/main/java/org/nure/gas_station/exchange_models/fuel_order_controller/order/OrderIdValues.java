package org.nure.gas_station.exchange_models.fuel_order_controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.enumerations.OrderType;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderIdValues extends OrderValues {

    public OrderIdValues(float amount, OrderType orderType, Date orderDate, long id) {
        super(amount, orderType, orderDate);
        this.id = id;
    }

    @JsonProperty("id")
    private long id;
}
