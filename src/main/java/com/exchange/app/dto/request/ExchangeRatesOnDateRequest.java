package com.exchange.app.dto.request;

import lombok.Builder;

@Builder
public record ExchangeRatesOnDateRequest(
        String date,
        String symbols,
        String base
) {
}
