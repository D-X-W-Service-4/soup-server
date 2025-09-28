package dxw.soup.backend.soupserver.domain.planner.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PlannerCreateRequest(
        @NotNull
        LocalDate date
) {
}