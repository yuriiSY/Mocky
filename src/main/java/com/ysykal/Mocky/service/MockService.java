package com.ysykal.Mocky.service;

import com.ysykal.Mocky.dto.CreateMockRequest;
import com.ysykal.Mocky.dto.MockResponse;
import com.ysykal.Mocky.entity.MockEndpoint;
import com.ysykal.Mocky.repository.MockEndpointRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MockService {

    private final MockEndpointRepository repository;

    public MockService(MockEndpointRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MockResponse createMock(CreateMockRequest request) {
        if (repository.existsByPathAndMethod(request.path(), request.method())) {
            throw new IllegalArgumentException("Mock with this path and method already exists");
        }

        MockEndpoint entity = MockEndpoint.builder()
                .path(request.path())
                .method(request.method().toUpperCase())
                .responseStatus(request.responseStatus())
                .responseBody(request.responseBody())
                .delayMs(request.delayMs() != null ? request.delayMs() : 0L)
                .failureRate(request.failureRate() != null ? request.failureRate() : 0)
                .build();

        MockEndpoint saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<MockResponse> getAllMocks() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void deleteMock(UUID id) {
        repository.deleteById(id);
    }

    private MockResponse mapToResponse(MockEndpoint entity) {
        return new MockResponse(
                entity.getId(),
                entity.getPath(),
                entity.getMethod(),
                entity.getResponseStatus(),
                entity.getResponseBody(),
                entity.getDelayMs(),
                entity.getFailureRate(),
                entity.getCreatedAt()
        );
    }
}
