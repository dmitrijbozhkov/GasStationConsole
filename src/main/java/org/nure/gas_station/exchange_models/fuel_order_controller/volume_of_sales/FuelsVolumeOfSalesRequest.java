package org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class FuelsVolumeOfSalesRequest extends VolumeOfSalesRequest {

    public FuelsVolumeOfSalesRequest(Date before, Date after, List<String> fuelNames) {
        super(before, after);
        this.fuelNames = fuelNames;
    }

    @JsonProperty("fuelNames")
    @NotEmpty(message = "Please provide list of fuels")
    private List<String> fuelNames;
}
