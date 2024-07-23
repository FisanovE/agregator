package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clouds {

    /*@JsonProperty("all")
    private Integer cloudiness;*/

    private int all;
}
