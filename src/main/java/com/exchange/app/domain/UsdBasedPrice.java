package com.exchange.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@IdClass(UsdBasedPrice.PK.class)
@RequiredArgsConstructor
@Table(name = "USDBASEDPRICE", schema = "exchange")
public class UsdBasedPrice {

    @Id
    private Long currencyId;

    @Id
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal usdPrice;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class PK implements Serializable {

        private Long currencyId;
        private LocalDate date;

    }


}
