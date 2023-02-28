package com.exchange.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSeriesResponse {
    private String base;
    private String start_date;
    private String end_date;
    private Map<String, Map<String, Double>> rates;
}
