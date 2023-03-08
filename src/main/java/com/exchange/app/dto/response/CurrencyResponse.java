package com.exchange.app.dto.response;

import com.exchange.app.dto.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class CurrencyResponse {

    private List<CurrencyDto> currencies;

}
