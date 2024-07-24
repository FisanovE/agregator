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
public class Wind {

    @JsonProperty("speed")
    private Double speed;

    @JsonProperty("deg")
    private Integer directionOfDegrees;

    @JsonProperty("gust")
    private Double gust;
}
