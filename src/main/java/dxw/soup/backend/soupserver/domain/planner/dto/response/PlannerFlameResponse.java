package dxw.soup.backend.soupserver.domain.planner.dto.response;

import java.util.List;

public record PlannerFlameResponse(
        List<PlannerFlameItem> flames
) {
    public static PlannerFlameResponse of(List<PlannerFlameItem> flames) {
        return new PlannerFlameResponse(flames);
    }
}