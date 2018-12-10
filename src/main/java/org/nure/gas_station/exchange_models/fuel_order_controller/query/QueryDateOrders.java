package org.nure.gas_station.exchange_models.fuel_order_controller.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class QueryDateOrders extends QueryOrders {

    public QueryDateOrders(int page,int amount, Date before, Date after) {
        super(page, amount);
        this.before = before;
        this.after = after;
    }

    @JsonProperty("before")
    @NotNull(message = "Please provide orders starting date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date before;
    @JsonProperty("after")
    @NotNull(message = "Please provide orders ending date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date after;
}
