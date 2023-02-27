package com.exchange.app.service;

import com.exchange.app.dto.response.ExchangeRatesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final RestTemplate template;

    private static final String API_KEY = "";

    public ExchangeRatesResponse getExchangeRates(String symbols, String base) {
        String apiUrl = "https://api.apilayer.com/fixer/latest?apikey=" + API_KEY;
        String currencies = "&symbols=" + symbols + "&base=" + base;
        String url = apiUrl + currencies;

        ResponseEntity<ExchangeRatesResponse> responseEntity = template.getForEntity(url,
                ExchangeRatesResponse.class);
        return responseEntity.getBody();
    }
}
