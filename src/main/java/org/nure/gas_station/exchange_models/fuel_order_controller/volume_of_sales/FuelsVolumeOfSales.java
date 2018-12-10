package org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelsVolumeOfSales {

    public FuelsVolumeOfSales(List<FuelVolumeOfSales> fuelVolumeOfSales) {
        this.fuelVolumeOfSales = fuelVolumeOfSales;
        this.overallVolumeOfSales = countOverallVolumeOfSales(fuelVolumeOfSales);
    }

    @JsonProperty("fuelVolumeOfSales")
    private List<FuelVolumeOfSales> fuelVolumeOfSales;
    @JsonProperty("overallVolumeOfSales")
    private float overallVolumeOfSales;

    private float countOverallVolumeOfSales(List<FuelVolumeOfSales> fuelVolumeOfSales) {
        return fuelVolumeOfSales
                .stream()
                .map(FuelVolumeOfSales::getVolumeOfSales)
                .reduce((float) 0, (current, next) -> current + next);
    }
}
