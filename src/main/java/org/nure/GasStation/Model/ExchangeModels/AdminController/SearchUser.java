package org.nure.GasStation.Model.ExchangeModels.AdminController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Value
public class SearchUser {
    String username;
    int page;
    int amount;
}
