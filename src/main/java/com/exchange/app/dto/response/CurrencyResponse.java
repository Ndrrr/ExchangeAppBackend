package com.exchange.app.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Map;

public record CurrencyResponse(

        @JsonProperty("symbols")
        Map<String, String> currencies

) {
}
