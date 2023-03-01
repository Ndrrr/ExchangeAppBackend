package com.exchange.app.service;

import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.*;

public interface CurrencyService {

    RateResponseDB getLatestExchangeRatesOnBase(RatesRequest request);

    CurrencyConvertingResponse getCurrencyConvertingResult(CurrencyConvertingRequest request);

    ExchangeRateFluctuationResponse getExchangeRateFluctuation(ExchangeRateFluctuationRequest request);

    void loadCurrencies();

    TimeSeriesResponse getRatesBasedOnDate(TimeSeriesRequest request);
}
