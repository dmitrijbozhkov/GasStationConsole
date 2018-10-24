package org.nure.GasStation.Model.ExchangeModels.FuelController;

import lombok.Value;
import org.nure.GasStation.Model.Fuel;

import java.util.List;

@Value
public class FuelList {
    private List<Fuel> fuels;
}
