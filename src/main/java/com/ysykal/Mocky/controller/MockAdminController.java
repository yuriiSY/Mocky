package com.ysykal.Mocky.controller;


import com.ysykal.Mocky.dto.CreateMockRequest;
import com.ysykal.Mocky.dto.MockResponse;
import com.ysykal.Mocky.service.MockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/mocks")
public class MockAdminController {

    private final MockService mockService;

    public MockAdminController(MockService mockService) {
        this.mockService = mockService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MockResponse createMock(@Valid @RequestBody CreateMockRequest request) {
        return mockService.createMock(request);
    }

    @GetMapping
    public List<MockResponse> getAllMocks() {
        return mockService.getAllMocks();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMock(@PathVariable UUID id) {
        mockService.deleteMock(id);
    }
}
