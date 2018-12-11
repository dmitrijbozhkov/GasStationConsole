package org.nure.gas_station.exchange_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDTO<T> extends ListDTO<T> {

    public PageDTO(List<T> content, Pageable page, long total) {
        super(content);
        this.page = page.getPageNumber();
        this.amount = page.getPageSize();
        this.total = total;
    }

    @JsonProperty("page")
    private int page;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("total")
    private long total;
}
