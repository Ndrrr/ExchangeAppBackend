package com.exchange.app.service;

import com.exchange.app.dto.request.ExchangeRatesRequest;
import com.exchange.app.dto.response.ExchangeRatesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExchangeRatesService {

    private final RestTemplate template;
    private static final String API = "https://api.apilayer.com/fixer/";

    private static final String API_KEY = "qN6hQxrhUWeDWlOrsIthOc7rmH4fTrcI";

    public ExchangeRatesResponse getExchangeRates(ExchangeRatesRequest request) {
        String apiUrl = API + "latest?apikey=" + API_KEY;
        String currencies = "&symbols=" + request.symbol() + "&base=" + request.base();
        String url = apiUrl + currencies;

        ResponseEntity<ExchangeRatesResponse> responseEntity = template.getForEntity(url,
                ExchangeRatesResponse.class);
        return responseEntity.getBody();
    }
}
