package com.example.agregator.aggregate;

import com.example.agregator.currency.CurrencyResponse;
import com.example.agregator.currency.CurrencyService;
import com.example.agregator.exceptions.ValidationException;
import com.example.agregator.weather.WeatherResponse;
import com.example.agregator.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AggregateService {


    private final CurrencyService currencyService;
    private final WeatherService weatherService;

    public AggregateInfo getAggregateInfo(String currency, String city, Double latitude, Double longitude) {

        AggregateInfo aggregateInfo = new AggregateInfo();
        CurrencyResponse currencyResponse;
        WeatherResponse weatherResponse;

        if (currency == null) {
            throw new ValidationException("Currency parameter must not be null");
        }

        if (city == null && latitude == null && longitude == null) {
            throw new ValidationException("All geocoding parameters must not be null");
        }

        currencyResponse = currencyService.getCurrencyRate(currency);

        if (city != null) {
            weatherResponse = weatherService.getDailyWeatherByCity(city);
        } else {
            weatherResponse = weatherService.getDailyWeatherByCoordinates(latitude, longitude);
        }

        aggregateInfo.setCurrencyRates(currencyResponse.getRates().getCurrencyRates());
        aggregateInfo.setWeathers(weatherResponse.getForecasts());

        return aggregateInfo;
    }
}
