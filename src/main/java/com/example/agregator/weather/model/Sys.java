package com.example.agregator.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sys {
    @JsonProperty("pod")
    private String partOfDay;
}
