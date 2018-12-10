package org.nure.gas_station.exchange_models.fuel_order_controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.enumerations.OrderType;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
public class CreateOrder extends OrderValues {

    public CreateOrder(float amount, OrderType orderType, Date orderDate, String fuelName) {
        super(amount, orderType, orderDate);
        this.fuelName = fuelName;
    }

    @JsonProperty("fuelName")
    @NotBlank(message = "Please provide fuel name")
    private String fuelName;
}
