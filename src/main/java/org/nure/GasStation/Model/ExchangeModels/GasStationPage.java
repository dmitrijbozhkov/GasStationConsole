package org.nure.GasStation.Model.ExchangeModels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class GasStationPage<T> extends PageImpl<T> {

    public GasStationPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public GasStationPage(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int number,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") Long totalElements,
                        @JsonProperty("pageable") JsonNode pageable,
                        @JsonProperty("last") boolean last,
                        @JsonProperty("totalPages") int totalPages,
                        @JsonProperty("sort") JsonNode sort,
                        @JsonProperty("first") boolean first,
                        @JsonProperty("numberOfElements") int numberOfElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public GasStationPage(List<T> content) {
        super(content);
    }

    public GasStationPage() {
        super(new ArrayList<T>());
    }

    public GasStationPage(Page<T> page) { super(page.getContent(), page.getPageable(), page.getTotalElements()); }

    public PageImpl<T> pageImpl() {
        return new PageImpl<T>(getContent(), new PageRequest(getNumber(),
                getSize(), getSort()), getTotalElements());
    }
}
