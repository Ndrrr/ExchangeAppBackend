package com.exchange.app.repository;

import com.exchange.app.domain.CurrencyConvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyConvertRepository extends JpaRepository<CurrencyConvert, Long> {
    @Query("select cc from CurrencyConvert cc where cc.user.id=:userId")
    List<CurrencyConvert> findAllByUserId(Long userId);
}
