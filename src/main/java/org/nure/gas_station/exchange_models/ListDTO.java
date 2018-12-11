package org.nure.gas_station.exchange_models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ListDTO<T> {

    public ListDTO(List<T> content) {
        this.content = content;
    }

    @JsonProperty("content")
    private List<T> content;
}
