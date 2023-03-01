package com.exchange.app.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rates", schema = "exchange")
public class Rates {
    @Id
    @GeneratedValue
    private Long id;
    private String base;
    private String symbols;
    private BigDecimal rate;
}
