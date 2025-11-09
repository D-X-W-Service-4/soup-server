package dxw.soup.backend.soupserver.domain.planner.dto.response;

import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PlannerResponse(
        Long plannerId,
        LocalDate date,
        boolean flame,
        PlannerFeedback feedback,
        List<PlannerItemDto> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PlannerResponse from(Planner planner, List<PlannerItemDto> items) {
        return new PlannerResponse(
                planner.getId(),
                planner.getDate(),
                planner.isFlame(),
                planner.getFeedback(),
                items,
                planner.getCreatedAt(),
                planner.getUpdatedAt()
        );
    }
}