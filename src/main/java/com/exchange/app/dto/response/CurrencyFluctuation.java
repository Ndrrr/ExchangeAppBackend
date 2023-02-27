package com.exchange.app.dto.response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyFluctuation {
    private BigDecimal start_rate;
    private BigDecimal end_rate;
    private BigDecimal change;
    private BigDecimal change_pct;
}
