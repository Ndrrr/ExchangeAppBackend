package com.exchange.app.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Currency")
@Table(name = "currency", schema = "exchange")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Currency {
    @SequenceGenerator(name = "currency_sequence", sequenceName = "currency_sequence",
            allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_sequence")
    private Long id;
    private String code;
    private String name;
}
