package com.exchange.app.dto.request;

import lombok.Builder;

@Builder
public record TimeSeriesRequest(
        String start_date,
        String end_date,
        String base,
        String symbol
) {
}
