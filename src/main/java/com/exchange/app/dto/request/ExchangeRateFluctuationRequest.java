package com.exchange.app.dto.request;

import lombok.Data;



@Data
public class ExchangeRateFluctuationRequest{
    private String start_date;
    private String end_date;
    private String symbol;
    private String base;
}
