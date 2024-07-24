package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("coord")
    private Coordinates coordinates;

    @JsonProperty("country")
    private String country;

    @JsonProperty("population")
    private Integer population;

    @JsonProperty("timezone")
    private Integer timezone;

    @JsonProperty("sunrise")
    private Long sunrise;

    @JsonProperty("sunset")
    private Long sunset;
}
