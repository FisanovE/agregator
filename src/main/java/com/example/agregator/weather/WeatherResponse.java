package com.example.agregator.weather;

import com.example.agregator.weather.model.City;
import com.example.agregator.weather.model.Forecast;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    @JsonProperty("list")
    private List<Forecast> forecasts;

    @JsonProperty("city")
    private City city;

}
