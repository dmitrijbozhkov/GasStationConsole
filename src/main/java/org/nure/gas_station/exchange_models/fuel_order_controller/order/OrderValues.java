package org.nure.gas_station.exchange_models.fuel_order_controller.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.model.enumerations.OrderType;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderValues {

    @JsonProperty("amount")
    @NotNull(message = "Please provide order amount")
    private float amount;
    @JsonProperty("orderType")
    @NotNull(message = "Please provide order type")
    private OrderType orderType;
    @JsonProperty("orderDate")
    @NotNull(message = "Please provide order date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date orderDate;
}
