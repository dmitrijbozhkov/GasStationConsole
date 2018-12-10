package org.nure.gas_station.exchange_models.admin_controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUser {
    @JsonProperty("username")
    @NotBlank(message = "Username cannot be epty")
    private String username;
    @JsonProperty("page")
    @Min(value = 0)
    @NotNull(message = "Page cannot be empty")
    int page;
    @JsonProperty("amount")
    @Min(value = 1)
    @NotNull(message = "Amount cannot be empty")
    int amount;
}
