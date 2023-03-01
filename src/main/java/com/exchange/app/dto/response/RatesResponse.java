package com.exchange.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record RatesResponse(
        @JsonProperty("base")
        String from,
        @JsonProperty("rates")
        Map<String, BigDecimal> rates
) {
}
