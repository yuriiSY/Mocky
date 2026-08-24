package com.ysykal.Mocky.service;

import com.ysykal.Mocky.entity.MockEndpoint;
import com.ysykal.Mocky.repository.MockEndpointRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class DynamicMockService {

    private final MockEndpointRepository mockRepository;
    private final Random random = new Random();

    public DynamicMockService(MockEndpointRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public ResponseEntity<String> processMockRequest(String path, String method) {
        Optional<MockEndpoint> mockOpt = mockRepository.findByPathAndMethod(path, method);

        if (mockOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Mock endpoint not found for path: " + path + "\"}");
        }

        MockEndpoint mock = mockOpt.get();

        if (mock.getDelayMs() != null && mock.getDelayMs() > 0) {
            try {
                Thread.sleep(mock.getDelayMs());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"error\": \"Interrupted during delay emulation\"}");
            }
        }

        if (mock.getFailureRate() != null && mock.getFailureRate() > 0) {
            if (random.nextInt(100) < mock.getFailureRate()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"error\": \"CHAOS_ENGINEERING_FAILURE: Simulated 500 error\"}");
            }
        }

        return ResponseEntity.status(mock.getResponseStatus())
                .body(mock.getResponseBody() != null ? mock.getResponseBody() : "");
    }
}
