package dxw.soup.backend.soupserver.domain.planner.controller;

import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerCreateRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerFeedbackRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerItemCheckRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerFlameResponse;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerResponse;
import dxw.soup.backend.soupserver.domain.planner.facade.PlannerFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestApiController("/v1/planners")
public class PlannerController implements PlannerApi {
    private final PlannerFacade plannerFacade;

    @PostMapping
    public CommonResponse<PlannerResponse> createPlanner(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody PlannerCreateRequest request
    ) {
        PlannerResponse response = plannerFacade.createPlanner(principal.getUserId(), request);
        return CommonResponse.ok(response);
    }

    @DeleteMapping("/{plannerId}")
    public CommonResponse<?> deletePlanner(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long plannerId
    ) {
        plannerFacade.deletePlanner(principal.getUserId(), plannerId);
        return CommonResponse.ok();
    }

    @PatchMapping("/{plannerId}/feedback")
    public CommonResponse<?> updatePlannerFeedback(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long plannerId,
            @Valid @RequestBody PlannerFeedbackRequest request
    ) {
        plannerFacade.updatePlannerFeedback(principal.getUserId(), plannerId, request.feedback());
        return CommonResponse.ok();
    }

    @PatchMapping("/items/{plannerItemId}/check")
    public CommonResponse<?> updatePlannerItemCheck(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long plannerItemId,
            @Valid @RequestBody PlannerItemCheckRequest request
    ) {
        plannerFacade.updatePlannerItemCheck(principal.getUserId(), plannerItemId, request.checked());
        return CommonResponse.ok();
    }

    @GetMapping
    public CommonResponse<PlannerResponse> getPlannerByDate(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam LocalDate date
    ) {
        PlannerResponse response = plannerFacade.getPlannerByDate(principal.getUserId(), date);
        return CommonResponse.ok(response);
    }

    @GetMapping("/flames")
    public CommonResponse<PlannerFlameResponse> getPlannerFlames(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        PlannerFlameResponse response = plannerFacade.getPlannerFlames(principal.getUserId(), startDate, endDate);
        return CommonResponse.ok(response);
    }
}