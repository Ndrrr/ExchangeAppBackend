package com.exchange.app.service;

import com.exchange.app.dto.request.CurrencyConvertingRequest;
import com.exchange.app.dto.request.ExchangeRateFluctuationRequest;
import com.exchange.app.dto.request.ExchangeRatesOnDateRequest;
import com.exchange.app.dto.request.ExchangeRatesRequest;
import com.exchange.app.dto.response.CurrencyConvertingResponse;
import com.exchange.app.dto.response.ExchangeRateFluctuationResponse;
import com.exchange.app.dto.response.ExchangeRatesResponse;

public interface CurrencyService {
    ExchangeRatesResponse getLatestExchangeRatesOnBase(ExchangeRatesRequest request);

    CurrencyConvertingResponse getCurrencyConvertingResult(Long userId, CurrencyConvertingRequest request);

    ExchangeRatesResponse getExchangeRatesOnDate(ExchangeRatesOnDateRequest request);

    ExchangeRateFluctuationResponse getExchangeRateFluctuation(ExchangeRateFluctuationRequest request);
}
