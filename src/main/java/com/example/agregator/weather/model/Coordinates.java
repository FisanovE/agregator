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
public class Coordinates {

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;
}
