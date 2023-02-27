package com.exchange.app.service;

import com.exchange.app.dto.request.CurrencyConvertingRequest;
import com.exchange.app.dto.request.ExchangeRatesRequest;
import com.exchange.app.dto.response.CurrencyConvertingResponse;
import com.exchange.app.dto.response.ExchangeRatesResponse;

public interface CurrencyService {
    ExchangeRatesResponse getLatestExchangeRatesOnBase(ExchangeRatesRequest request);

    CurrencyConvertingResponse getCurrencyConvertingResult(Long userId, CurrencyConvertingRequest request);
}
