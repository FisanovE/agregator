package com.example.agregator.weather;

import com.example.agregator.configurations.ApiKeys;
import com.example.agregator.exceptions.RequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Сервис погоды, обрабатывает и возвращает информацию, полученную с веб-сервиса погоды.
 */
@Slf4j
@Service
@AllArgsConstructor
public class WeatherService {

    private final ApiKeys apiKeys;
    private static final String BASE_API_URL = "https://api.openweathermap.org";
    private static final String FORECAST_API_URL_SUFFIX = "/data/2.5/forecast";

    private final HttpClient client;
    private final ObjectMapper objectMapper;

    /**
     * Возвращает информацию, полученную с сервиса погоды - прогноз погоды на 5 дней по переданным координатам.
     *
     * @param latitude  - координата широты
     * @param longitude - координата долготы
     * @return объект WeatherResponse с информацией о прогнозе погоды
     * @throws RequestException если сервис погоды не ответит на запрос.
     */
    public WeatherResponse getForecastByCoordinates(Double latitude, Double longitude) {
        try {
            URI uri = URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX + "?units=metric&lat=" + latitude + "&lon=" +
                    longitude + "&appid=" + apiKeys.getWeatherToken());
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), WeatherResponse.class);
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
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
            URI uri = URI.create(BASE_API_URL + FORECAST_API_URL_SUFFIX + "?units=metric&q=" + cityName + "&appid=" + apiKeys.getWeatherToken());
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), WeatherResponse.class);
        } catch (Exception e) {
            throw new RequestException(e.getMessage());
        }
    }

    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }
}
