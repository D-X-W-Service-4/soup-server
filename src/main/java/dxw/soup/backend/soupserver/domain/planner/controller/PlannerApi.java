package dxw.soup.backend.soupserver.domain.planner.controller;

import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerCreateRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerFeedbackRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.request.PlannerItemCheckRequest;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerFlameResponse;
import dxw.soup.backend.soupserver.domain.planner.dto.response.PlannerResponse;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "플래너")
public interface PlannerApi {

    @Operation(summary = "플래너 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<PlannerResponse> createPlanner(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody PlannerCreateRequest request
    );

    @Operation(summary = "플래너 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> deletePlanner(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long plannerId
    );

    @Operation(summary = "플래너 피드백 남기기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> updatePlannerFeedback(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long plannerId,
            @Valid @RequestBody PlannerFeedbackRequest request
    );

    @Operation(summary = "플래너 항목 체크")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> updatePlannerItemCheck(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long plannerItemId,
            @Valid @RequestBody PlannerItemCheckRequest request
    );

    @Operation(summary = "날짜별 플래너 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<PlannerResponse> getPlannerByDate(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam LocalDate date
    );

    @Operation(summary = "기간별 플래너 불꽃 여부 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<PlannerFlameResponse> getPlannerFlames(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    );
}
