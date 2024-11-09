package com.springapitemplate.api.controller.health.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class HealthCheckResponse {

    @Schema(description = "서버 health 상태", example = "ok", requiredMode = REQUIRED)
    private final String health;

    @Schema(description = "현재 실행중인 profile", example = "[dev, prod]", requiredMode = REQUIRED)
    private final List<String> activeProfiles;

    private HealthCheckResponse(String health, List<String> activeProfiles) {
        this.health = health;
        this.activeProfiles = activeProfiles;
    }

    public static HealthCheckResponse of(String health, List<String> activeProfiles) {
        return new HealthCheckResponse(health, activeProfiles);
    }

}
