package com.exchange.app.service;

import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.CurrencyConvertingResponse;
import com.exchange.app.dto.response.ExchangeRateFluctuationResponse;
import com.exchange.app.dto.response.ExchangeRatesResponse;
import com.exchange.app.dto.response.TimeSeriesResponse;

public interface CurrencyService {
    ExchangeRatesResponse getLatestExchangeRatesOnBase(ExchangeRatesRequest request);

    CurrencyConvertingResponse getCurrencyConvertingResult(Long userId, CurrencyConvertingRequest request);

    ExchangeRateFluctuationResponse getExchangeRateFluctuation(ExchangeRateFluctuationRequest request);

    TimeSeriesResponse getRatesBasedOnDate(TimeSeriesRequest request);
}
