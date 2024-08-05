package com.example.agregator.currency;

import com.example.agregator.configurations.ApiKeys;
import com.example.agregator.exceptions.RequestException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Сервис валюты, обрабатывает и возвращает информацию, полученную с веб-сервиса валюты.
 */
@Slf4j
@Service
@AllArgsConstructor
public class CurrencyService {

    private final ApiKeys apiKeys;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    /**
     * Возвращает информацию, полученную с сервиса валюты.
     *
     * @param currency - идентификатор валютной пары, может содержать несколько значений через запятую
     * @return объект CurrencyResponse с информацией о курсе валют
     * @throws RequestException если сервис валюты не ответит на запрос.
     */
    public CurrencyResponse getCurrencyRate(String currency) {
        try {
            URI uri = URI.create("https://currate.ru/api/?get=rates&pairs=" + currency + "&key=" + apiKeys.getCurrencyToken());
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            CurrencyResponse currencyResponse = objectMapper.readValue(response.body(), CurrencyResponse.class);
            if (currencyResponse.getStatus() == 500) {
                throw new RequestException("Invalid 'currency' parameter format: " + currency);
            }
            if (currencyResponse.getStatus() == 403) {
                throw new RequestException("Invalid currency api-key");
            }
            return currencyResponse;
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
