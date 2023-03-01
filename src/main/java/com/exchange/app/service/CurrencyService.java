package com.exchange.app.service;

import com.exchange.app.domain.Currency;
import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.*;

import java.io.FileNotFoundException;
import java.util.List;

public interface CurrencyService {

    ExchangeRatesResponse getLatestExchangeRatesOnBase(ExchangeRatesRequest request);

    CurrencyConvertingResponse getCurrencyConvertingResult(CurrencyConvertingRequest request);

    ExchangeRateFluctuationResponse getExchangeRateFluctuation(ExchangeRateFluctuationRequest request);

    void loadCurrencies();

    TimeSeriesResponse getRatesBasedOnDate(TimeSeriesRequest request);
}
