package dxw.soup.backend.soupserver.global.controller;

import static dxw.soup.backend.soupserver.global.common.code.SuccessCode.OK;

import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiController("/health-check")
public class HealthCheckController {
    @GetMapping
    public ApiResponse<String> getHealthCheck() {
        return ApiResponse.success(OK);
    }
}
