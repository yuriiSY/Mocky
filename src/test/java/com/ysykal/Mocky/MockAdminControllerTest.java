package com.ysykal.Mocky;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MockAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /api/admin/mocks — Successfully creating a new mock")
    void shouldCreateMockSuccessfully() throws Exception {
        String jsonPayload = """
            {
                "path": "/users/99",
                "method": "GET",
                "responseStatus": 200,
                "responseBody": "{\\"id\\": 99, \\"name\\": \\"TestUser\\"}",
                "delayMs": 100,
                "failureRate": 0
            }
            """;

        mockMvc.perform(post("/api/admin/mocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.path").value("/users/99"))
                .andExpect(jsonPath("$.method").value("GET"))
                .andExpect(jsonPath("$.delayMs").value(100));
    }

    @Test
    @DisplayName("GET /api/admin/mocks — Retrieving a list of all mocks")
    void shouldReturnListOfMocks() throws Exception {
        mockMvc.perform(get("/api/admin/mocks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}