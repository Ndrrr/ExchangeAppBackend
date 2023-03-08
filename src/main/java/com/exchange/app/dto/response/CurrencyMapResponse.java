package com.exchange.app.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Map;

public record CurrencyMapResponse(

        @JsonProperty("symbols")
        Map<String, String> currencies

) {
}
