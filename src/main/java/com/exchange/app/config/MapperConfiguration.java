package com.exchange.app.config;

import com.exchange.app.mapper.CurrencyMapper;
import com.exchange.app.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public CurrencyMapper currencyMapper() {
        return CurrencyMapper.INSTANCE;
    }
}
