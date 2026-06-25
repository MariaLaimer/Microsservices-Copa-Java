package br.edu.atitus.authservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.edu.atitus.authservice.entities.UserType;
import br.edu.atitus.authservice.entities.UserTypeDeserializer;

public record SignupDTO(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password,
        @JsonProperty("phone") String phone,
        @JsonProperty("type") @JsonDeserialize(using = UserTypeDeserializer.class) UserType type) {
}