package com.springapitemplate.infra.feigntest;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.springapitemplate.api.controller.health.response.HealthCheckResponse;

@Getter
@NoArgsConstructor
public class HealthCheckTestResponse {

    private String code;
    private HttpStatus status;
    private String message;
    private HealthCheckResponse data;

    public HealthCheckTestResponse(String code, HttpStatus status, String message, HealthCheckResponse data) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
