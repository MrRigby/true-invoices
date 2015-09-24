package com.github.mrrigby.trueinvoices.model.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Optional;

/**
 * @author MrRigby
 */
public class OptionalSerializer<T> extends JsonSerializer<Optional<T>> {

    @Override
    public void serialize(Optional<T> value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {

        if (value == null || !value.isPresent()) {
            gen.writeNull();
        } else {
            gen.writeObject(value.get());
        }
    }

    public final static class LongOptionalSerializer extends OptionalSerializer<Long> {}
}
