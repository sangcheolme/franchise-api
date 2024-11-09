package com.springapitemplate.infra.feigntest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8080", name = "helloClient")
public interface HealthCheckClient {

    @GetMapping(value = "/api/v1/health", consumes = "application/json")
    HealthCheckTestResponse healthCheck();

}
