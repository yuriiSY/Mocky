package com.ysykal.Mocky;

import com.ysykal.Mocky.repository.MockEndpointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DynamicMockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockEndpointRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("GET /mock/users/1 — Returns the stored response mock")
    void shouldReturnMockedResponse() throws Exception {

        String createPayload = """
        {
            "path": "/users/1",
            "method": "GET",
            "responseStatus": 200,
            "responseBody": "{\\"id\\": 1, \\"name\\": \\"Alex\\"}",
            "delayMs": 0,
            "failureRate": 0
        }
        """;

        mockMvc.perform(post("/api/admin/mocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mockMvc.perform(get("/mock/users/1"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\": 1, \"name\": \"Alex\"}"));
    }

    @Test
    @DisplayName("GET /mock/unknown — Returns 404 if the mock is not registered")
    void shouldReturn404WhenMockNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/mock/unknown-route"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound());
    }

}
