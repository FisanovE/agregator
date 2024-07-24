package com.example.agregator;

import com.example.agregator.exceptions.RequestException;
import com.example.agregator.weather.WeatherResponse;
import com.example.agregator.weather.WeatherService;
import com.example.agregator.weather.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private WeatherService weatherService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Double latitude = 59.0472;
    Double longitude = 53.4186;
    String cityName = "Magnitogorsk";

    Coordinates coordinates = new Coordinates(latitude, longitude);
    City city = new City(1, cityName, coordinates, "RU", 450000, 18000,
            1721606650L, 1721664961L);
    Main main = new Main(17.24, 17.32, 14.05, 17.24, 1008, 1008, 962, 88);
    Weather weather = new Weather(804,"Clouds","overcast clouds");
    Clouds clouds = new Clouds(100);
    Wind wind = new Wind(2.16, 26, 3.49);
    Sys partOfDay = new Sys("n");
    Forecast forecast = new Forecast(1721757600L, main, List.of(weather), clouds, wind, 10000, 0.0, partOfDay, "2024-07-23 18:00:00");
    WeatherResponse expectedResponse = new WeatherResponse(List.of(forecast), city);


    @Test
    void shouldWeatherByCoordinatesReturned_whenParamCoordinatesValid() throws IOException, InterruptedException {
        String mockResponseBody = objectMapper.writeValueAsString(expectedResponse);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockObjectMapper.readValue(mockResponseBody, WeatherResponse.class)).thenReturn(expectedResponse);

        WeatherResponse actualResponse = weatherService.getDailyWeatherByCoordinates(latitude, longitude);

        assertEquals(actualResponse.getCity(), city);
        assertEquals(actualResponse.getForecasts().size(), 1);
        assertEquals(actualResponse.getForecasts().get(0), forecast);
        verify(mockHttpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
    }

    @Test
    void shouldWeatherByCityNameReturned_whenParamCityNameValid() throws IOException, InterruptedException {
        String mockResponseBody = objectMapper.writeValueAsString(expectedResponse);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockObjectMapper.readValue(mockResponseBody, WeatherResponse.class)).thenReturn(expectedResponse);

        WeatherResponse actualResponse = weatherService.getDailyWeatherByCity(cityName);

        assertEquals(actualResponse.getCity(), city);
        assertEquals(actualResponse.getForecasts().size(), 1);
        assertEquals(actualResponse.getForecasts().get(0), forecast);
        verify(mockHttpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
    }

    @Test
    public void shouldRequestExceptionReturned_whenParamCoordinatesNotValid() throws Exception {

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new RuntimeException("Network error"));

        RequestException exception = assertThrows(RequestException.class,
                () -> weatherService.getDailyWeatherByCoordinates(latitude, longitude));

        assertEquals("Error getting weather for coordinates: " + latitude + "  " + longitude, exception.getMessage());
    }

    @Test
    public void shouldRequestExceptionReturned_whenParamCityNameNotValid() throws Exception {

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new RuntimeException("Network error"));

        RequestException exception = assertThrows(RequestException.class,
                () -> weatherService.getDailyWeatherByCity(cityName));

        assertEquals("Error getting weather for city: " + cityName, exception.getMessage());
    }
}