package org.nure.gas_station.exchange_models.fuel_order_controller.volume_of_sales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.exchange_models.fuel_controller.RequestFuel;
import org.nure.gas_station.model.Fuel;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.enumerations.OrderType;

import java.util.List;


@Data
@NoArgsConstructor
public class FuelVolumeOfSales extends RequestFuel {

    public FuelVolumeOfSales(String fuelName, float volumeOfSales) {
        super(fuelName);
        this.volumeOfSales = volumeOfSales;
    }

    public FuelVolumeOfSales(Fuel fuel, List<FuelOrder> fuelOrders) {
        super(fuel.getFuelName());
        this.volumeOfSales = countVolumeOfSales(fuelOrders, fuel.getFuelTariff().getExchangeRate());
    }

    @JsonProperty("volumeOfSales")
    private float volumeOfSales;

    private float countVolumeOfSales(List<FuelOrder> fuelOrders, float exchangeRate) {
        return fuelOrders
                .stream()
                .map((o) -> {
                    switch (o.getOrderType()) {
                        case CURRENCY_BY_FUEL:
                            return o.getAmount() * exchangeRate;
                        case FUEL_BY_CURRENCY:
                            return o.getAmount();
                            default:
                                throw new RuntimeException("Order type not implemented!");
                    }

                }).reduce((float) 0, (previous, next) -> {
                    return previous + next;
                });
    }
}
