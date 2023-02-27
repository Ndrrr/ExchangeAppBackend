package com.exchange.app.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record CurrencyConvertingRequest(
        @Min(3)
        String from,
        @Min(3)
        String to,
        String amount
) {
}
