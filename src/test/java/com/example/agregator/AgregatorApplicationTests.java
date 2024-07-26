package com.example.agregator;


import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgregatorApplicationTests {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }


    @Test
    public void shouldCode200AndResponseReturned_whenParamCityValid() {
        given().
                pathParam("currency1", "USDRUB").
                pathParam("currency2", "EURRUB").
                pathParam("city", "Magnitogorsk").
                when().
                get(uri + "/aggregate?currency={currency1}&currency={currency2}&q={city}").
                then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void shouldCode500AndErrorReturned_whenParamCityNotValid() {
        given().
                pathParam("currency1", "USDRUB").
                pathParam("currency2", "EURRUB").
                pathParam("city", "").
                when().
                get(uri + "/aggregate?currency={currency1}&currency={currency2}&q={city}").
                then().
                assertThat().
                statusCode(400);
    }

    @Test
    public void shouldCode200AndResponseReturned_whenParamCoordinatesValid() {
        given().
                pathParam("currency1", "USDRUB").
                pathParam("currency2", "EURRUB").
                pathParam("lat", "59.0472").
                pathParam("lon", "53.4186").
                when().
                get(uri + "/aggregate?currency={currency1}&currency={currency2}&lat={lat}&lon={lon}").
                then().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schema.json"));
    }

    @Test
    public void shouldCode500AndErrorReturned_whenParamCoordinatesNotValid() {
        given().
                pathParam("currency1", "USDRUB").
                pathParam("currency2", "EURRUB").
                pathParam("lat", "").
                pathParam("lon", "53.4186").
                when().
                get(uri + "/aggregate?currency={currency1}&currency={currency2}&lat={lat}&lon={lon}").
                then().
                statusCode(400).
                log().
                all();
    }
}