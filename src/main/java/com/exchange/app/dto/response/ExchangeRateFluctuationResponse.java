package com.exchange.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateFluctuationResponse {

    private String base;
    private LocalDate start_date;
    private LocalDate end_date;
    private Map<String, CurrencyFluctuation> rates;

}
