package com.exchange.app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency-convert", schema = "exchange")
@Builder
public class CurrencyConvert {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(nullable = false, unique = true)
    private Double result;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
