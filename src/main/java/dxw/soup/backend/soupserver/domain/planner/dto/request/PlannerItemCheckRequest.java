package dxw.soup.backend.soupserver.domain.planner.dto.request;

import jakarta.validation.constraints.NotNull;

public record PlannerItemCheckRequest(
        @NotNull
        Boolean checked
) {
}