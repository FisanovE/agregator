package com.example.agregator.currency;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    @JsonProperty("data")
    private Data rates;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {

        private Map<String, String> currencyRates = new HashMap<>();

        @JsonAnySetter
        public void addCurrencyRate(String key, String value) {
            currencyRates.put(key, value);
        }
    }
}
