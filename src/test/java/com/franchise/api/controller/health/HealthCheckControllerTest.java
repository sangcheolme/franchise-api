package com.franchise.api.controller.health;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.franchise.support.auth.config.SecurityConfig;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = HealthCheckController.class)
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("서버 상태 체크 API")
    @Test
    void healthCheck() throws Exception {
        mockMvc.perform(get("/api/v1/health")
                        .contentType("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("API Health Check"))
                .andExpect(jsonPath("$.data.health").value("OK"))
                .andExpect(jsonPath("$.data.activeProfiles").isArray());
    }

}
