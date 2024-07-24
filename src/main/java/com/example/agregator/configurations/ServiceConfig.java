package com.example.agregator.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class ServiceConfig {

    @Bean
    HttpClient httpClient(){
        return HttpClient.newHttpClient();
    }

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
