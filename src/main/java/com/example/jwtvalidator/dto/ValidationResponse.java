package com.example.jwtvalidator.dto;

public record ValidationResponse(boolean valid, String status, String subject, String message) {}
