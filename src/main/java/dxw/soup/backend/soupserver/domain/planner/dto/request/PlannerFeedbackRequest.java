package dxw.soup.backend.soupserver.domain.planner.dto.request;

import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "플래너 피드백 요청 DTO")
public record PlannerFeedbackRequest(

        @NotNull
        @Schema(description = "피드백 유형 (예: GOOD, NORMAL, BAD)", example = "GOOD")
        PlannerFeedback feedback
) {
}