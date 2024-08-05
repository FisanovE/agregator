package com.example.agregator.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Object data;

    public boolean isDataArray() {
        return data instanceof Iterable;
    }

    public boolean isDataMap() {
        return data instanceof Map;
    }


    public Map<String, String> getRates() {
        if (isDataMap()) {
            return (Map<String, String>) data;
        } else {
            return null;
        }
    }

}
