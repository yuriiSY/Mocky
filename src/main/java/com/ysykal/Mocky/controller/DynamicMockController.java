package com.ysykal.Mocky.controller;

import com.ysykal.Mocky.service.DynamicMockService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/mock", produces = MediaType.APPLICATION_JSON_VALUE)
public class DynamicMockController {

    private final DynamicMockService dynamicMockService;

    public DynamicMockController(DynamicMockService dynamicMockService) {
        this.dynamicMockService = dynamicMockService;
    }

    @RequestMapping("/**")
    public CompletableFuture<ResponseEntity<String>> handleDynamicRequest(HttpServletRequest request) {
        String mockPath = request.getRequestURI().substring("/mock".length());
        String httpMethod = request.getMethod();

        return CompletableFuture.supplyAsync(() ->
                dynamicMockService.processMockRequest(mockPath, httpMethod)
        );
    }
}