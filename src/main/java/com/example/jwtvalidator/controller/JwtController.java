package com.example.jwtvalidator.controller;

import com.example.jwtvalidator.dto.TokenResponse;
import com.example.jwtvalidator.dto.ValidationResponse;
import com.example.jwtvalidator.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class JwtController {
    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/demo-token")
    @Operation(summary = "Generate a demo JWT (valid for 1 hour)")
    public TokenResponse generate(@RequestParam(defaultValue = "mokshith") @NotBlank String username) {
        return new TokenResponse(jwtService.createDemoToken(username), "Bearer",
            "Demo token generated. Use it in the Swagger Authorize dialog.");
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate the supplied JWT", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ValidationResponse> validate(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ValidationResponse(false, "INVALID", null,
                    "Authorization header is missing or must use Bearer <token>."));
        }

        String token = authorization.substring(7).trim();
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ValidationResponse(false, "INVALID", null, "Token is empty."));
        }

        try {
            Claims claims = jwtService.validate(token);
            return ResponseEntity.ok(new ValidationResponse(true, "VALID",
                claims.getSubject(), "JWT signature and expiration are valid."));
        } catch (JwtException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ValidationResponse(false, "INVALID", null,
                    "JWT is invalid, expired, malformed, or has an incorrect signature."));
        }
    }

    @GetMapping("/health")
    public String health() {
        return "JWT Validator API is running";
    }
}
