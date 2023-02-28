package com.exchange.app.controller;

import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.CurrencyConvertingResponse;
import com.exchange.app.dto.response.ExchangeRateFluctuationResponse;
import com.exchange.app.dto.response.ExchangeRatesResponse;
import com.exchange.app.dto.response.TimeSeriesResponse;
import com.exchange.app.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class CurrencyExchangeController {
    private final CurrencyService currencyService;

    @GetMapping("/latest")
    public ResponseEntity<ExchangeRatesResponse> getLatestRates(@RequestBody ExchangeRatesRequest request) {
        return ResponseEntity.ok(currencyService.getLatestExchangeRatesOnBase(request));
    }

    @GetMapping("/{userId}/convert")
    public ResponseEntity<CurrencyConvertingResponse> getConvertingResult(@PathVariable(name = "userId") Long userId,
                                                                          @RequestBody CurrencyConvertingRequest request) {
        return ResponseEntity.ok(currencyService.getCurrencyConvertingResult(userId, request));
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
