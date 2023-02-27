package com.exchange.app.dto.response;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CurrencyConvertingResponse(
        Double result,
        @Temporal(TemporalType.DATE)
        LocalDate date
) {
}
