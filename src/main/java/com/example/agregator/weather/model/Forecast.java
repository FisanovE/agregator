package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Forecast {

    @JsonProperty("dt")
    private Long forecastTime;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private List<Weather> weathers;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("pop")
    private Double probabilityOfPrecipitation;

    @JsonProperty("sys")
    private Sys partOfDay;

    @JsonProperty("dt_txt")
    private String forecastTime_txt;
}