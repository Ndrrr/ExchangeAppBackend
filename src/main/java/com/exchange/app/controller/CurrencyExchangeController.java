package com.exchange.app.controller;

import com.exchange.app.dto.request.CurrencyConvertingRequest;
import com.exchange.app.dto.request.ExchangeRateFluctuationRequest;
import com.exchange.app.dto.request.TimeSeriesRequest;
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

    @GetMapping("/currencies")
    public ResponseEntity<CurrencyResponse> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @GetMapping("/latest")
    public ResponseEntity<ExchangeRatesResponse> getLatestRates(@RequestParam String symbol,
                                                                @RequestParam String base) {
        return ResponseEntity.ok(currencyService.getLatestExchangeRatesOnBase(symbol, base));
    }

    @GetMapping("/convert")
    public ResponseEntity<CurrencyConvertingResponse> getConvertingResult(@RequestBody CurrencyConvertingRequest request) {
        return ResponseEntity.ok(currencyService.getCurrencyConvertingResult(request));
    }

    @PostMapping("/time-series")
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
