package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Main {

    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double temperatureFeelsLike;

    @JsonProperty("temp_min")
    private Double temperatureMinimal;

    @JsonProperty("temp_max")
    private Double temperatureMaximal;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("sea_level")
    private Integer pressureAtSeaLevel;

    @JsonProperty("grnd_level")
    private Integer pressureAtGroundLevel;

    @JsonProperty("humidity")
    private Integer humidity;
}
