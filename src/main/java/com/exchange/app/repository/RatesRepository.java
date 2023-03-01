package com.exchange.app.repository;

import com.exchange.app.domain.Rates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {
    @Query("select r from Rates r where r.base=:from and r.symbols=:to")
    Optional<Rates> findRatesByFromAndTo(String from, String to);
}
