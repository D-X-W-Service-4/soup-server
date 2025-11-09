package dxw.soup.backend.soupserver.domain.planner.dto.request;

import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import jakarta.validation.constraints.NotNull;

public record PlannerFeedbackRequest(
        @NotNull
        PlannerFeedback feedback
) {
}