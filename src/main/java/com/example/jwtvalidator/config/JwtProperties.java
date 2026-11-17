package com.example.jwtvalidator.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProperties {
    private final String secret;

    public JwtProperties(@Value("${app.jwt.secret}") String secret) {
        this.secret = secret;
    }

    public Key signingKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
