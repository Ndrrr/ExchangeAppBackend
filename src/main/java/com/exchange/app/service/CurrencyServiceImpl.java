package com.exchange.app.service;

import com.exchange.app.domain.Currency;
import com.exchange.app.domain.CurrencyConvert;
import com.exchange.app.domain.Rates;
import com.exchange.app.dto.request.*;
import com.exchange.app.dto.response.*;
import com.exchange.app.handler.errors.CurrencyConvertingException;
import com.exchange.app.handler.errors.CurrencyNotFoundException;
import com.exchange.app.handler.ErrorCode;
import com.exchange.app.handler.errors.DateException;
import com.exchange.app.handler.errors.UserNotFoundException;
import com.exchange.app.repository.CurrencyConvertRepository;
import com.exchange.app.repository.CurrencyRepository;
import com.exchange.app.repository.RatesRepository;
import com.exchange.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final RestTemplate template;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final RatesRepository ratesRepository;
    private final CurrencyConvertRepository currencyConvertRepository;
    private static final String API = "https://api.apilayer.com/fixer/";
    private static final String API_KEY = "pm77YUyZWXeXrgOC0rAP4jT6OCk148W4";

    @Override
    public RateResponseDB getLatestExchangeRatesOnBase(RatesRequest request) {
        Optional<Rates> rates = ratesRepository
                .findRatesByFromAndTo(request.from(), request.to());

        if (rates.isEmpty()) {
            log.debug("RATES REQUESTED TO API");
            RatesResponse ratesResponse = askRateToAPI(request);
            Rates rts = saveRateToDb(request, ratesResponse);
            return getRateResponseDB(rts);
        }
        return getRateResponseDB(rates.get());
    }

    private RateResponseDB getRateResponseDB(Rates rates) {
        return RateResponseDB.builder()
                .rate(rates.getRate())
                .build();
    }

    private Rates saveRateToDb(RatesRequest request, RatesResponse ratesResponse) {
        Rates r = Rates.builder()
                .base(request.from())
                .symbols(request.to())
                .rate(ratesResponse.rates().get(request.to()))
                .build();
        log.debug("RATES SAVED TO DATABASE");
        return ratesRepository.save(r);
    }

    private RatesResponse askRateToAPI(RatesRequest request) {
        String apiUrl = "%slatest?apikey=%s".formatted(API, API_KEY);
        String currencies = "&symbols=%s&base=%s"
                .formatted(request.to().toUpperCase(),
                        request.from().toUpperCase());
        String url = apiUrl + currencies;

        ResponseEntity<RatesResponse> responseEntity = template.getForEntity(url,
                RatesResponse.class);
        if (currencyChecking(responseEntity.getStatusCode()))
            throw new CurrencyNotFoundException(ErrorCode.CURRENCY_NOT_FOUND.code(), "Such currency not exist");
        return responseEntity.getBody();
    }

    @Override
    public CurrencyConvertingResponse getCurrencyConvertingResult(CurrencyConvertingRequest request) {
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

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND.code(),
                        "User not found"));

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
    public void loadCurrencies() {
        if (currencyRepository.findAll().isEmpty()) {
            log.debug("CURRENCIES REQUESTED FROM API");
            CurrencyResponse response = getDataFromAPI();
            Set<String> currencyCodes = response.currencies().keySet();
            List<Currency> currencies = currencyCodes.stream().map(key -> Currency.builder()
                    .code(key)
                    .name(response.currencies().get(key))
                    .build()).toList();
            currencyRepository.saveAll(currencies);
        }
        log.debug("CURRENCIES ARE EXIST IN DATABASE");
    }

    @Override
    public TimeSeriesResponse getRatesBasedOnDate(TimeSeriesRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sdate = LocalDate.parse(request.start_date(), formatter);
        LocalDate edate = LocalDate.parse(request.end_date(), formatter);
        if (edate.isBefore(sdate)) throw new DateException(ErrorCode.DATE_FAIL.code(), "Date not correct");
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

    private CurrencyResponse getDataFromAPI() {
        String apiUrl = "%ssymbols?apikey=%s".formatted(API, API_KEY);
        ResponseEntity<CurrencyResponse> responseEntity = template.getForEntity(apiUrl,
                CurrencyResponse.class);
        return responseEntity.getBody();
    }

}
