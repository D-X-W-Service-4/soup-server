package dxw.soup.backend.soupserver.domain.planner.dto.response;

import java.time.LocalDate;

public record PlannerFlameItem(
        LocalDate date,
        boolean flame
) {
    public static PlannerFlameItem of(LocalDate date, boolean flame) {
        return new PlannerFlameItem(date, flame);
    }
}