package com.springapitemplate.api.controller.feigntest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.springapitemplate.api.ApiResponse;
import com.springapitemplate.infra.feigntest.HealthCheckClient;
import com.springapitemplate.infra.feigntest.HealthCheckTestResponse;

@RequiredArgsConstructor
@RestController
public class HealthFeignTestController {

    private final HealthCheckClient healthCheckClient;

    @GetMapping("/api/v1/health/feign-test")
    public ApiResponse<HealthCheckTestResponse> healthCheck() {
        return ApiResponse.ok("OpenFeign Test", healthCheckClient.healthCheck());
    }

}
