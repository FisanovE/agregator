package com.example.agregator;

import com.example.agregator.configurations.ApiKeys;
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
import java.util.Map;

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

    @Mock
    private ApiKeys mockApiKeys;

    @InjectMocks
    private CurrencyService currencyService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldCurrencyResponseReturned_whenParamCurrencyValid() throws Exception {

        String currency = "USDRUB";
        String rateValue = "64.1824";

        CurrencyResponse expectedResponse = new CurrencyResponse();
        expectedResponse.setStatus(200);
        expectedResponse.setMessage("rates");
        expectedResponse.setData(Map.of(currency, rateValue));

        String mockResponseBody = objectMapper.writeValueAsString(expectedResponse);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(mockResponse.body()).thenReturn(mockResponseBody);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockObjectMapper.readValue(mockResponseBody, CurrencyResponse.class)).thenReturn(expectedResponse);

        CurrencyResponse actualResponse = currencyService.getCurrencyRate(currency);
        Map<String, String> data = (Map<String, String>) actualResponse.getData();

        assertEquals(rateValue, data.get(currency));
        verify(mockHttpClient).send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()));
    }

    @Test
    public void shouldRequestExceptionReturned_whenParamCurrencyNotValid() throws Exception {
        String currency = "USD";
        String message = "Network error";

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new RuntimeException(message));

        RequestException exception = assertThrows(RequestException.class, () -> currencyService.getCurrencyRate(currency));

        assertEquals(message, exception.getMessage());
    }

}