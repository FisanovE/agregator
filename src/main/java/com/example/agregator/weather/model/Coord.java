package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coord {
/*    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lon")
    private double lon;*/

    private double lat;
    private double lon;
}
