package com.github.mrrigby.trueinvoices.model.jsonsupport;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author MrRigby
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {

        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
}
