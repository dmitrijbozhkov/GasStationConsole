package org.nure.gas_station.exchange_models.AdminController;

import lombok.Value;

@Value
public class SearchUser {
    String username;
    int page;
    int amount;
}
