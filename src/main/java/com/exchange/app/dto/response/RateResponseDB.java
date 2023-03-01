package com.exchange.app.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RateResponseDB(
        BigDecimal rate
) {
}
