package com.github.mrrigby.trueinvoices.model.jsonsupport;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author MrRigby
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonToken currentToken = p.getCurrentToken();
        if (JsonToken.VALUE_STRING == currentToken) {
            String str = p.getText().trim();
            if (str.length() == 0) {
                return null;
            } else {
                return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
            }
        } else {
            return null;
        }
    }
}
