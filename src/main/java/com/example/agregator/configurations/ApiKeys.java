package com.example.agregator.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApiKeys {

    @Value("${currency.token}")
    private String currencyToken;

    @Value("${weather.token}")
    private String weatherToken;

}
