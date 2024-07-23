package com.example.agregator.aggregate;


import com.example.agregator.weather.model.WeatherData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AggregateInfo {
    private Map<String, String> currencyRates;
    private List<WeatherData> weathers;
}
