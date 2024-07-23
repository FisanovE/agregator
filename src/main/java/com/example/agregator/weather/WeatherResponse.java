package com.example.agregator.weather;

import com.example.agregator.weather.model.City;
import com.example.agregator.weather.model.WeatherData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    /*@JsonProperty("list")
    private List<DayWeather> weathers;*/

    private String cod;
    private int message;
    private int cnt;
    private List<WeatherData> list;
    private City city;

}
