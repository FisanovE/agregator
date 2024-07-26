package com.example.agregator;

import com.example.agregator.aggregate.AggregateInfo;
import com.example.agregator.aggregate.AggregateService;
import com.example.agregator.currency.CurrencyResponse;
import com.example.agregator.currency.CurrencyService;
import com.example.agregator.exceptions.ValidationException;
import com.example.agregator.weather.WeatherResponse;
import com.example.agregator.weather.WeatherService;
import com.example.agregator.weather.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AggregateServiceTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    AggregateService aggregateService;

    String currency = "USDRUB";
    String rateValue = "64.1824";

    Double latitude = 59.0472;
    Double longitude = 53.4186;
    String cityName = "Magnitogorsk";
    String forecastTime = "2024-07-25 17:00:00";
    Integer visibility = 10000;
    Double probabilityOfPrecipitation = 0.0;

    CurrencyResponse.Data data = new CurrencyResponse.Data(Map.of(currency, rateValue));
    CurrencyResponse currencyResponse = new CurrencyResponse(data);

    Coordinates coordinates = new Coordinates(latitude, longitude);
    City city = new City(1, cityName, coordinates, "RU", 450000, 18000,
            1721606650L, 1721664961L);
    Main main = new Main(17.24, 17.32, 14.05, 17.24, 1008, 1008, 962, 88);
    Weather weather = new Weather(804, "Clouds", "overcast clouds");
    Clouds clouds = new Clouds(100);
    Wind wind = new Wind(2.16, 26, 3.49);
    Sys partOfDay = new Sys("n");
    Forecast forecast = new Forecast(forecastTime, main, List.of(weather), clouds, wind, visibility,
            probabilityOfPrecipitation, partOfDay, forecastTime);
    WeatherResponse weatherResponse = new WeatherResponse(List.of(forecast), city);
//    AggregateInfo expectedAggregateInfo = new AggregateInfo(Map.of(currency, rateValue), List.of(forecast));

    @Test
    void shouldAggregateInfoByCityReturned_whenParamCityAndCoordinatesValid() {
        when(currencyService.getCurrencyRate(currency)).thenReturn(currencyResponse);
        when(weatherService.getForecastByCity(cityName)).thenReturn(weatherResponse);

        AggregateInfo actualAggregateInfo = aggregateService.getAggregateInfo(currency, cityName, latitude, longitude);

        assertTrue(actualAggregateInfo.getCurrencyRates().containsKey(currency));
        assertEquals(actualAggregateInfo.getCurrencyRates().get(currency), rateValue);

        assertEquals(actualAggregateInfo.getWeathers().size(), 1);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getForecastTime(), forecastTime);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getMain(), main);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getWeathers().get(0), weather);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getClouds(), clouds);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getWind(), wind);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getVisibility(), visibility);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getProbabilityOfPrecipitation(), probabilityOfPrecipitation);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getForecastTime_txt(), forecastTime);
    }

    @Test
    void shouldAggregateInfoByCityReturned_whenParamCityValid() {
        when(currencyService.getCurrencyRate(currency)).thenReturn(currencyResponse);
        when(weatherService.getForecastByCity(cityName)).thenReturn(weatherResponse);

        AggregateInfo actualAggregateInfo = aggregateService.getAggregateInfo(currency, cityName, null, null);

        assertTrue(actualAggregateInfo.getCurrencyRates().containsKey(currency));
        assertEquals(actualAggregateInfo.getCurrencyRates().get(currency), rateValue);

        assertEquals(actualAggregateInfo.getWeathers().size(), 1);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getForecastTime(), forecastTime);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getMain(), main);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getWeathers().get(0), weather);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getClouds(), clouds);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getWind(), wind);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getVisibility(), visibility);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getProbabilityOfPrecipitation(), probabilityOfPrecipitation);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getForecastTime_txt(), forecastTime);
    }

    @Test
    void shouldAggregateInfoByCoordinatesReturned_whenParamCoordinatesValid() {
        when(currencyService.getCurrencyRate(currency)).thenReturn(currencyResponse);
        when(weatherService.getForecastByCoordinates(latitude, longitude)).thenReturn(weatherResponse);

        AggregateInfo actualAggregateInfo = aggregateService.getAggregateInfo(currency, null, latitude, longitude);

        assertTrue(actualAggregateInfo.getCurrencyRates().containsKey(currency));
        assertEquals(actualAggregateInfo.getCurrencyRates().get(currency), rateValue);

        assertEquals(actualAggregateInfo.getWeathers().size(), 1);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getForecastTime(), forecastTime);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getMain(), main);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getWeathers().get(0), weather);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getClouds(), clouds);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getWind(), wind);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getVisibility(), visibility);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getProbabilityOfPrecipitation(), probabilityOfPrecipitation);
        assertEquals(actualAggregateInfo.getWeathers().get(0).getForecastTime_txt(), forecastTime);
    }

    @Test
    public void shouldValidationExceptionReturned_whenParamCurrencyIsNull() throws Exception {

        ValidationException exception = assertThrows(ValidationException.class,
                () -> aggregateService.getAggregateInfo(null, null, latitude, latitude));

        assertEquals("Currency parameter must not be null", exception.getMessage());
    }

    @Test
    public void shouldValidationExceptionReturned_whenParamLongitudeIsNull() throws Exception {

        ValidationException exception = assertThrows(ValidationException.class,
                () -> aggregateService.getAggregateInfo(currency, null, latitude, null));

        assertEquals("All geocoding parameters must not be null at the same time", exception.getMessage());
    }

    @Test
    public void shouldValidationExceptionReturned_whenParamLatitudeIsNull() throws Exception {

        ValidationException exception = assertThrows(ValidationException.class,
                () -> aggregateService.getAggregateInfo(currency, null, null, longitude));

        assertEquals("All geocoding parameters must not be null at the same time", exception.getMessage());
    }

    @Test
    public void shouldValidationExceptionReturned_whenParamCityNameIsEmpty() throws Exception {

        ValidationException exception = assertThrows(ValidationException.class,
                () -> aggregateService.getAggregateInfo(currency, "", latitude, longitude));

        assertEquals("City Name parameter must not be empty", exception.getMessage());
    }
}