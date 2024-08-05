package com.example.agregator.aggregate;


import com.example.agregator.weather.model.Forecast;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AggregateInfo {
//    private Map<String, String> currencyRates;
    private Object currencyRates;
    private List<Forecast> weathers;
}
