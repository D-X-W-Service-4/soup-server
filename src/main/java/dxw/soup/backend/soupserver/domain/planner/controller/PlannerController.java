package dxw.soup.backend.soupserver.domain.planner.controller;

import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerCreateRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerFeedbackRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerItemCheckRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerFlameResponse;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerResponse;
import dxw.soup.backend.soupserver.domain.planner.service.PlannerService;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestApiController("v1/planners")
public class PlannerController {
    private final PlannerService plannerService;

    @PostMapping
    public ApiResponse<PlannerResponse> createPlanner(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody PlannerCreateRequest request
    ) {
        PlannerResponse response = plannerService.createPlanner(principal.getUserId(), request);
        return ApiResponse.ok(response);
    }
}