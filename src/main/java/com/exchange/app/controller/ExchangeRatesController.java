package com.exchange.app.controller;

import com.exchange.app.dto.request.ExchangeRatesRequest;
import com.exchange.app.dto.response.ExchangeRatesResponse;
import com.exchange.app.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeRatesController {
    private final ExchangeRatesService exchangeRatesService;

    @GetMapping("/latest")
    public ResponseEntity<ExchangeRatesResponse> getLatestRates(@RequestBody ExchangeRatesRequest request) {
        return ResponseEntity.ok(exchangeRatesService.getExchangeRates(request));
    }
}
