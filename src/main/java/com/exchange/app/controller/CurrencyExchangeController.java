package com.exchange.app.controller;

import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.*;
import com.exchange.app.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class CurrencyExchangeController {
    private final CurrencyService currencyService;

    @GetMapping
    public void loadCurrencies() {
        currencyService.loadCurrencies();
    }

    @GetMapping("/rate")
    public ResponseEntity<RateResponseDB> getLatestRates(@RequestBody RatesRequest request) {
        return ResponseEntity.ok(currencyService.getLatestExchangeRatesOnBase(request));
    }

    @GetMapping("/convert")
    public ResponseEntity<CurrencyConvertingResponse> getConvertingResult(@RequestBody CurrencyConvertingRequest request) {
        return ResponseEntity.ok(currencyService.getCurrencyConvertingResult(request));
    }

    @GetMapping("/time-series")
    public ResponseEntity<TimeSeriesResponse> getRatesBasedOnDate(@RequestBody TimeSeriesRequest request) {
        return ResponseEntity.ok(currencyService.getRatesBasedOnDate(request));
    }

    @GetMapping("/fluctuation")
    public ResponseEntity<ExchangeRateFluctuationResponse> getExchangeRateFluctuation(
            @RequestBody ExchangeRateFluctuationRequest request
    ) {
        return ResponseEntity.ok(currencyService.getExchangeRateFluctuation(request));
    }

}
