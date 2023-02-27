package com.exchange.app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Entity(name = "ExchangeRates")
@Table(name = "exchange_rates", schema = "exchange")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ExchangeRates {

    @Id
    @GeneratedValue
    private Long id;

    private Long timeStamp;

    private String base;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @ElementCollection
    private Map<String, Double> rates;

}
