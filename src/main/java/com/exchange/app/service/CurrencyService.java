package com.exchange.app.service;

import com.exchange.app.dto.request.CurrencyConvertingRequest;
import com.exchange.app.dto.request.ExchangeRateFluctuationRequest;
import com.exchange.app.dto.request.TimeSeriesRequest;
import com.exchange.app.dto.response.*;

public interface CurrencyService {

    ExchangeRatesResponse getLatestExchangeRatesOnBase(String symbol, String base);

    CurrencyConvertingResponse getCurrencyConvertingResult(CurrencyConvertingRequest request);

    ExchangeRateFluctuationResponse getExchangeRateFluctuation(ExchangeRateFluctuationRequest request);

    TimeSeriesResponse getRatesBasedOnDate(TimeSeriesRequest request);

    CurrencyResponse getAllCurrencies();

}
