package com.example.agregator.currency;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponse {

    @JsonProperty("data")
    private Data rates;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Data {

        private Map<String, String> currencyRates = new HashMap<>();

        @JsonAnySetter
        public void addCurrencyRate(String key, String value) {
            currencyRates.put(key, value);
        }
    }
}
