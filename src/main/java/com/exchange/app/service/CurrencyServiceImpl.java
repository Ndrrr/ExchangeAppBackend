package com.exchange.app.service;

import com.exchange.app.domain.CurrencyConvert;
import com.exchange.app.domain.User;
import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.CurrencyConvertingResponse;
import com.exchange.app.dto.response.ExchangeRateFluctuationResponse;
import com.exchange.app.dto.response.ExchangeRatesResponse;
import com.exchange.app.dto.response.TimeSeriesResponse;
import com.exchange.app.handler.errors.CurrencyConvertingException;
import com.exchange.app.handler.errors.CurrencyNotFoundException;
import com.exchange.app.handler.ErrorCode;
import com.exchange.app.handler.errors.DateException;
import com.exchange.app.handler.errors.UserNotFoundException;
import com.exchange.app.repository.CurrencyConvertRepository;
import com.exchange.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate template;
    private final UserRepository userRepository;
    private final CurrencyConvertRepository currencyConvertRepository;
    private static final String API = "https://api.apilayer.com/fixer/";
    private static final String API_KEY = "qN6hQxrhUWeDWlOrsIthOc7rmH4fTrcI";

    @Override
    public ExchangeRatesResponse getLatestExchangeRatesOnBase(ExchangeRatesRequest request) {
        String apiUrl = "%slatest?apikey=%s".formatted(API, API_KEY);
        String currencies = "&symbols=%s&base=%s"
                .formatted(request.symbol().toUpperCase(),
                        request.base().toUpperCase());

        String url = apiUrl + currencies;

        ResponseEntity<ExchangeRatesResponse> responseEntity = template.getForEntity(url,
                ExchangeRatesResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyNotFoundException(ErrorCode.CURRENCY_NOT_FOUND.code(), "Such currency not exist");
        return responseEntity.getBody();
    }

    @Override
    public CurrencyConvertingResponse getCurrencyConvertingResult(Long userId, CurrencyConvertingRequest request) {
        String apiUrl = "%sconvert?apikey=%s".formatted(API, API_KEY);
        String currencies = "&to=%s&from=%s&amount=%s"
                .formatted(request.to().toUpperCase(),
                        request.from().toUpperCase(),
                        request.amount().toUpperCase());

        String url = apiUrl + currencies;

        ResponseEntity<CurrencyConvertingResponse> responseEntity = template.getForEntity(url,
                CurrencyConvertingResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyConvertingException(ErrorCode.CURRENCY_CONVERTING_FAILED.code(),
                    "Something went wrong on converting currencies");
        var user = findUserById(userId);

        CurrencyConvert cc = CurrencyConvert.builder()
                .user(user)
                .date(responseEntity.getBody().date())
                .result(responseEntity.getBody().result())
                .build();
        currencyConvertRepository.save(cc);

        return responseEntity.getBody();
    }

    @Override
    public ExchangeRateFluctuationResponse getExchangeRateFluctuation(ExchangeRateFluctuationRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sdate = LocalDate.parse(request.getStart_date(), formatter);
        LocalDate edate = LocalDate.parse(request.getEnd_date(), formatter);
        if (edate.isBefore(sdate)) throw new DateException(ErrorCode.DATE_FAIL.code(), "Date not correct");
        String apiUrl = "%sfluctuation?apikey=%s".formatted(API, API_KEY);
        String currencies = "&base=%s&start_date=%s&end_date=%s&symbols=%s"
                .formatted(request.getBase().toUpperCase(),
                        request.getStart_date(),
                        request.getEnd_date(),
                        request.getSymbol().toUpperCase());
        String url = apiUrl + currencies;
        ResponseEntity<ExchangeRateFluctuationResponse> responseEntity = template.getForEntity(url,
                ExchangeRateFluctuationResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyNotFoundException(ErrorCode.CURRENCY_NOT_FOUND.code(), "Such currency not exist");
        return responseEntity.getBody();
    }

    @Override
    public TimeSeriesResponse getRatesBasedOnDate(TimeSeriesRequest request) {
        String apiUrl = "%stimeseries?apikey=%s".formatted(API, API_KEY);
        String currencies = "&base=%s&symbols=%s&start_date=%s&end_date=%s"
                .formatted(request.base(),
                        request.symbol(),
                        request.start_date(),
                        request.end_date());
        String url = apiUrl + currencies;
        ResponseEntity<TimeSeriesResponse> responseEntity = template.getForEntity(url,
                TimeSeriesResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyNotFoundException(ErrorCode.CURRENCY_NOT_FOUND.code(), "Such currency not exist");
        return responseEntity.getBody();
    }

    private boolean currencyChecking(HttpStatusCode status) {
        return !status.is2xxSuccessful();
    }

    private User findUserById(Long id) {
        var user = userRepository.findById(id);
        if (user.isPresent()) return user.get();
        throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND.code(),
                "User not found");
    }
}
