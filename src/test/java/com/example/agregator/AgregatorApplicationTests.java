package com.example.agregator;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
class AgregatorApplicationTests {

    @Test
    public void testGetEndpoint() {
        RestAssured.port = 8080;
        given()
                .get("http://localhost:8080/aggregate?currency=USDRUB&currency=EURRUB&q=Magnitogorsk")
                .then()
                .statusCode(200);
//                .body("currencyRates", notNullValue());
    }
}