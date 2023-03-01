package com.exchange.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RatesRequest(
        @JsonProperty("base")
        String from,
        @JsonProperty("symbols")
        String to
) {
}
