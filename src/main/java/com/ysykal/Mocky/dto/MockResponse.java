package com.ysykal.Mocky.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MockResponse(
        UUID id,
        String path,
        String method,
        Integer responseStatus,
        String responseBody,
        Long delayMs,
        Integer failureRate,
        OffsetDateTime createdAt
) {}
