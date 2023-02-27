package com.exchange.app.service;

import com.exchange.app.dto.request.CurrencyConvertingRequest;
import com.exchange.app.dto.request.ExchangeRatesRequest;
import com.exchange.app.dto.response.CurrencyConvertingResponse;
import com.exchange.app.dto.response.ExchangeRatesResponse;
import com.exchange.app.handler.errors.CurrencyConvertingException;
import com.exchange.app.handler.errors.CurrencyNotFoundException;
import com.exchange.app.handler.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate template;
    private static final String API = "https://api.apilayer.com/fixer/";
    private static final String API_KEY = "qN6hQxrhUWeDWlOrsIthOc7rmH4fTrcI";

    @Override
    public ExchangeRatesResponse getLatestExchangeRatesOnBase(ExchangeRatesRequest request) {
        String apiUrl = API + "latest?apikey=" + API_KEY;
        String currencies = "&symbols=" + request.symbol().toUpperCase() +
                "&base=" + request.base().toUpperCase();
        String url = apiUrl + currencies;

        ResponseEntity<ExchangeRatesResponse> responseEntity = template.getForEntity(url,
                ExchangeRatesResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyNotFoundException(ErrorCode.CURRENCY_NOT_FOUND.code(), "Such currency not exist");
        return responseEntity.getBody();
    }

    @Override
    public CurrencyConvertingResponse getCurrencyConvertingResult(CurrencyConvertingRequest request) {
        String apiUrl = API + "convert?apikey=" + API_KEY;
        String currencies = "&to=" + request.to().toUpperCase() +
                "&from=" + request.from().toUpperCase() +
                "&amount=" + request.amount().toUpperCase();
        String url = apiUrl + currencies;

        ResponseEntity<CurrencyConvertingResponse> responseEntity = template.getForEntity(url,
                CurrencyConvertingResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyConvertingException(ErrorCode.CURRENCY_CONVERTING_FAILED.code(),
                    "Something went wrong on converting currencies");

        return responseEntity.getBody();
    }

    private boolean currencyChecking(HttpStatusCode status) {
        return !status.is2xxSuccessful();
    }
}
