package br.edu.atitus.authservice.entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UserTypeDeserializer extends JsonDeserializer<UserType> {

    @Override
    public UserType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();

        if (token == JsonToken.VALUE_NUMBER_INT) {
            int ordinal = p.getIntValue();
            UserType[] values = UserType.values();
            if (ordinal < 0 || ordinal >= values.length) {
                throw new IOException("Valor inválido para UserType: " + ordinal);
            }
            return values[ordinal];
        }

        if (token == JsonToken.VALUE_STRING) {
            String text = p.getText();
            if (text == null || text.isBlank()) {
                return null;
            }
            for (UserType value : UserType.values()) {
                if (value.name().equalsIgnoreCase(text.trim())) {
                    return value;
                }
            }
            throw new IOException("Valor inválido para UserType: " + text);
        }

        if (token == JsonToken.VALUE_NULL) {
            return null;
        }

        throw new IOException("Não foi possível desserializar UserType a partir do token: " + token);
    }
}