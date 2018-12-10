package org.nure.gas_station.exchange_models.fuel_order_controller.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nure.gas_station.exchange_models.admin_controller.UserDetails;
import org.nure.gas_station.exchange_models.fuel_controller.FuelDetails;
import org.nure.gas_station.exchange_models.fuel_tariff_controller.TariffDetails;
import org.nure.gas_station.model.FuelOrder;
import org.nure.gas_station.model.enumerations.OrderType;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDetails extends OrderValues {

    public OrderDetails(float amount, OrderType orderType, Date orderDate, FuelDetails fuel, TariffDetails tariff, UserDetails user) {
        super(amount, orderType, orderDate);
        this.fuel = fuel;
        this.tariff = tariff;
        this.user = user;
    }

    public OrderDetails(FuelOrder fuelOrder) {
        super(fuelOrder.getAmount(), fuelOrder.getOrderType(), fuelOrder.getOrderDate());
        this.fuel = new FuelDetails(fuelOrder.getFuel());
        this.tariff = new TariffDetails(fuelOrder.getFuelTariff());
        this.user = new UserDetails(fuelOrder.getGasStationUser());
    }

    @JsonProperty("fuel")
    private FuelDetails fuel;
    @JsonProperty("tariff")
    private TariffDetails tariff;
    @JsonProperty("user")
    private UserDetails user;
}
