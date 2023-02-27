package com.exchange.app.dto.request;

import lombok.Builder;

@Builder
public record ExchangeRatesRequest(
        String symbol,
        String base
) {
}
