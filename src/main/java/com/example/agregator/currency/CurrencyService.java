package com.example.agregator.currency;

import com.example.agregator.exceptions.RequestException;
import com.example.agregator.exceptions.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.agregator.utils.Constants.CURRENCY_TOKEN;

/**
 * Сервис валюты, обрабатывает и возвращает информацию, полученную с веб-сервиса валюты.
 */
@Slf4j
@Service
public class CurrencyService {
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    CurrencyService(HttpClient httpClient, ObjectMapper objectMapper){
        this.client = httpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Возвращает информацию, полученную с сервиса валюты.
     *
     * @param currency - идентификатор валютной пары, может содержать несколько значений через запятую
     * @return объект CurrencyResponse с информацией о курсе валют
     * @throws RequestException если сервис валюты не ответит на запрос.
     */
    public CurrencyResponse getCurrencyRate(String currency) {
        try {
            URI uri = URI.create("https://currate.ru/api/?get=rates&pairs=" + currency + "&key=" + CURRENCY_TOKEN);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), CurrencyResponse.class);

        } catch (Exception e) {
            throw new RequestException("Error getting value for currency: " + currency);
        }
    }

    private static HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder(uri)
                .GET()
                .build();
    }
}
