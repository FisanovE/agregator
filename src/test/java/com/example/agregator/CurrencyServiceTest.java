package com.example.agregator;

import com.example.agregator.currency.CurrencyResponse;
import com.example.agregator.currency.CurrencyService;
import com.example.agregator.exceptions.RequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private CurrencyService currencyService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldCurrencyResponseReturned_whenStringCurrencyValid() throws Exception {

        int status = 200;
        String message = "ok";
        String currency = "USDRUB";
        String rateValue = "64.1824";

        CurrencyResponse.Data data = new CurrencyResponse.Data();
        data.addCurrencyRate(currency, rateValue);

        CurrencyResponse expectedResponse = new CurrencyResponse();
        expectedResponse.setRates(data);

        String mockResponseBody = objectMapper.writeValueAsString(expectedResponse);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockObjectMapper.readValue(mockResponseBody, CurrencyResponse.class)).thenReturn(expectedResponse);

        CurrencyResponse response = currencyService.getCurrencyRate(currency);

        assertEquals(rateValue, response.getRates().getCurrencyRates().get(currency));

        verify(mockHttpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
    }

    @Test
    public void shouldCurrencyExceptionReturned_whenStringCurrencyNotValid() throws Exception {
        String currency = "USD";

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new RuntimeException("Network error"));

        RequestException exception = assertThrows(RequestException.class, () -> currencyService.getCurrencyRate(currency));

        assertEquals("Error getting value for currency: " + currency, exception.getMessage());
    }

}