package com.exchange.app.mapper;

import com.exchange.app.domain.Currency;
import com.exchange.app.dto.CurrencyDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface CurrencyMapper {
    CurrencyMapper INSTANCE = Mappers.getMapper(CurrencyMapper.class);

    CurrencyDto toCurrencyDto(Currency currency);

    List<CurrencyDto> toCurrencyDtoList(List<Currency> currencies);

}
