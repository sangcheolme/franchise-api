package com.franchise.api.controller.feigntest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.franchise.api.ApiResponse;
import com.franchise.infra.feigntest.HealthCheckClient;
import com.franchise.infra.feigntest.HealthCheckTestResponse;

@RequiredArgsConstructor
@RestController
public class HealthFeignTestController {

    private final HealthCheckClient healthCheckClient;

    @GetMapping("/api/v1/health/feign-test")
    public ApiResponse<HealthCheckTestResponse> healthCheck() {
        return ApiResponse.ok("OpenFeign Test", healthCheckClient.healthCheck());
    }

}
