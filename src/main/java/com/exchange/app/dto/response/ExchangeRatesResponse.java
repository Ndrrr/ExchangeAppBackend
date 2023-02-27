package com.exchange.app.dto.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;

@Builder
public record ExchangeRatesResponse(
        Long timestamp,
        String base,

        @Temporal(TemporalType.DATE)
        LocalDate date,
        Map<String, Double> rates

) {
}
