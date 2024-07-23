package com.example.agregator.weather;

import com.example.agregator.exceptions.RequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.agregator.common.Constants.WEATHER_TOKEN;

@Slf4j
@Service
public class WeatherService {
    private static final String BASE_API_URL = "https://api.openweathermap.org";
    private static final String FORECAST_API_URL_SUFFIX = "/data/2.5/forecast";

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    WeatherService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.client = httpClient;
        this.objectMapper = objectMapper;
    }

    public WeatherResponse getDailyWeatherByCoordinates(Double latitude, Double longitude) {
        try {
            URI uri = URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX + "?units=metric&lat=" + latitude + "&lon=" +
                    longitude + "&appid=" + WEATHER_TOKEN);
            HttpRequest request = buildRequest(uri);
            log.info("{}", request);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("{}", response.body());
            return objectMapper.readValue(response.body(), WeatherResponse.class);
        } catch (Exception e) {
            throw new RequestException("Error getting weather for coordinates: " + latitude + "  " + longitude);
        }
    }

    public WeatherResponse getDailyWeatherByCity(String city) {
        try {
            URI uri = URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX + "?units=metric&q=" + city + "&appid=" + WEATHER_TOKEN);
            HttpRequest request = buildRequest(uri);
            log.info("{}", request);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("{}", response.body());
            return objectMapper.readValue(response.body(), WeatherResponse.class);
        } catch (Exception e) {
            throw new RequestException("Error getting weather for city: " + city);
        }
    }

    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }
}
