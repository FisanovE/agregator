package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {

    @JsonProperty("dt")
    private long forecastTime;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private List<Weather> weathers;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("pop")
    private double probabilityOfPrecipitation;

    @JsonProperty("sys")
    private Sys partOfDay;

    @JsonProperty("dt_txt")
    private String forecastTime_txt;
}
