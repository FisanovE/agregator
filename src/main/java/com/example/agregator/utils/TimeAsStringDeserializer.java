package com.example.agregator.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.example.agregator.utils.Constants.DATE_TIME_PATTERN;

public class TimeAsStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        long timestampInSeconds = jsonParser.getValueAsLong();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampInSeconds), ZoneId.systemDefault()).format(formatter);
    }
}