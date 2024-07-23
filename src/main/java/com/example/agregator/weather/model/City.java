package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("coord")
    private Coordinates coordinates;

    @JsonProperty("country")
    private String country;

    @JsonProperty("population")
    private int population;

    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("sunrise")
    private long sunrise;

    @JsonProperty("sunset")
    private long sunset;
}
