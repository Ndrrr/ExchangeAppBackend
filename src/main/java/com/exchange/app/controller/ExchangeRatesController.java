package com.exchange.app.controller;

import com.exchange.app.dto.response.ExchangeRatesResponse;
import com.exchange.app.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeRatesController {
    private final ExchangeRatesService exchangeRatesService;

    @GetMapping("/latest")
    public ResponseEntity<ExchangeRatesResponse> getLatestRates(
            @RequestParam("symbol") String symbol,
            @RequestParam("base") String base
    ) {
        return ResponseEntity.ok(exchangeRatesService.getExchangeRates(symbol, base));
    }
}
