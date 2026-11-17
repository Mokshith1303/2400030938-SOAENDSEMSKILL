package com.example.jwtvalidator.service;

import com.example.jwtvalidator.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private final JwtProperties properties;

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    public String createDemoToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 60 * 60 * 1000);
        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(properties.signingKey())
            .compact();
    }

    public Claims validate(String token) {
        return Jwts.parser()
            .verifyWith((javax.crypto.SecretKey) properties.signingKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
