package com.ysykal.Mocky.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateMockRequest(
        @NotBlank(message = "Path is required")
        @Pattern(regexp = "^/.*", message = "Path must start with '/'")
        String path,

        @NotBlank(message = "Method is required")
        @Pattern(regexp = "^(GET|POST|PUT|DELETE|PATCH)$", message = "Invalid HTTP method")
        String method,

        @NotNull
        @Min(100) @Max(599)
        Integer responseStatus,

        String responseBody,

        @Min(0)
        Long delayMs,

        @Min(0) @Max(100)
        Integer failureRate
) {}
