package com.example.agregator.weather;

import com.example.agregator.common.TimestampDeserializer;
import com.example.agregator.weather.model.Clouds;
import com.example.agregator.weather.model.Main;
import com.example.agregator.weather.model.Weather;
import com.example.agregator.weather.model.Wind;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayWeather {

    @JsonProperty("dt")
    @JsonDeserialize(using = TimestampDeserializer.class)
    private LocalDateTime date;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private Weather weather;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("visibility")
    private Integer visibility;
}
