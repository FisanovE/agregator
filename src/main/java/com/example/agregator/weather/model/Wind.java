package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {

    @JsonProperty("speed")
    private Double speed;

    @JsonProperty("deg")
    private Integer directionOfDegrees;

    @JsonProperty("gust")
    private Double gust;
}
