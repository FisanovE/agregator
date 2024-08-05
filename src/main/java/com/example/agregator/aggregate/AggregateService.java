package com.example.agregator.aggregate;

import com.example.agregator.currency.CurrencyResponse;
import com.example.agregator.currency.CurrencyService;
import com.example.agregator.exceptions.ValidationException;
import com.example.agregator.weather.WeatherResponse;
import com.example.agregator.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Агрегирующий сервис, агрегирует и возвращает информацию, полученную с сервисов валюты и погоды.
 */
@Service
@RequiredArgsConstructor
public class AggregateService {

    private final CurrencyService currencyService;
    private final WeatherService weatherService;

    /**
     * Возвращает агрегированную информацию, полученную с сервисов валюты и погоды.
     *
     * @param currency - идентификатор валютной пары, может содержать несколько значений через запятую
     * @param cityName - название города, имеет приоритет перед поиском по координатам
     * @param latitude - координата широты
     * @param longitude - координата долготы
     * @return объект AggregateInfo с агрегированной информацией
     * @throws ValidationException если параметр currency равен null, если все геопараметры одновременно равны null,
     * если параметр cityName пуст.
     */
    public AggregateInfo getAggregateInfo(String currency, String cityName, Double latitude, Double longitude) {

        AggregateInfo aggregateInfo = new AggregateInfo();
        CurrencyResponse currencyResponse;
        WeatherResponse weatherResponse;

        if (currency == null) {
            throw new ValidationException("Currency parameter must not be null");
        }

        if (cityName == null && (latitude == null || longitude == null)) {
            throw new ValidationException("All geocoding parameters must not be null at the same time");
        }

        if (cityName != null && cityName.isEmpty()) {
            throw new ValidationException("City Name parameter must not be empty");
        }

        currencyResponse = currencyService.getCurrencyRate(currency);

        if (cityName != null) {
            weatherResponse = weatherService.getForecastByCity(cityName);
        } else {
            weatherResponse = weatherService.getForecastByCoordinates(latitude, longitude);
        }

        aggregateInfo.setCurrencyRates(currencyResponse.getData());
        aggregateInfo.setWeathers(weatherResponse.getForecasts());

        return aggregateInfo;
    }
}
