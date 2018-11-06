package com.nibado.example.streambench;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.List;

public class User {
    public final String firstName;
    public final String lastName;
    public final String eMail;
    public final int yearOfBirth;
    public final List<Role> roles;
    public final LocalDateTime registered;
    public final LocalDateTime lastLogin;

    public User(String firstName, String lastName, String eMail, int yearOfBirth, List<Role> roles, LocalDateTime registered, LocalDateTime lastLogin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.yearOfBirth = yearOfBirth;
        this.roles = roles;
        this.registered = registered;
        this.lastLogin = lastLogin;
    }

    public String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public enum Role {
        USER,
        ADMIN
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
}
