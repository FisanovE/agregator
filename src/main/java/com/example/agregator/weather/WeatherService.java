package com.example.agregator.weather;

import com.example.agregator.exceptions.RequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.agregator.utils.Constants.WEATHER_TOKEN;

/**
 * Сервис погоды, обрабатывает и возвращает информацию, полученную с веб-сервиса погоды.
 */
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

    /**
     * Возвращает информацию, полученную с сервиса погоды - прогноз погоды на 5 дней по переданным координатам.
     *
     * @param latitude - координата широты
     * @param longitude - координата долготы
     * @return объект WeatherResponse с информацией о прогнозе погоды
     * @throws RequestException если сервис погоды не ответит на запрос.
     */
    public WeatherResponse getForecastByCoordinates(Double latitude, Double longitude) {
        try {
            URI uri = URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX + "?units=metric&lat=" + latitude + "&lon=" +
                    longitude + "&appid=" + WEATHER_TOKEN);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), WeatherResponse.class);
        } catch (Exception e) {
            throw new RequestException("Error getting weather for coordinates: " + latitude + "  " + longitude);
        }
    }

    /**
     * Возвращает информацию, полученную с сервиса погоды - прогноз погоды на 5 дней по переданному названию города.
     *
     * @param cityName - название города
     * @return объект WeatherResponse с информацией о прогнозе погоды
     * @throws RequestException если сервис погоды не ответит на запрос.
     */
    public WeatherResponse getForecastByCity(String cityName) {
        try {
            URI uri = URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX + "?units=metric&q=" + cityName + "&appid=" + WEATHER_TOKEN);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), WeatherResponse.class);
        } catch (Exception e) {
            throw new RequestException("Error getting weather for city: " + cityName);
        }
    }

    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }
}
