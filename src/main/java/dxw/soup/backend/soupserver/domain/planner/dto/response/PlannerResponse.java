package dxw.soup.backend.soupserver.domain.planner.dto.response;

import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.enums.PlannerFeedback;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "플래너 상세 응답 DTO")
public record PlannerResponse(

        @Schema(description = "플래너 ID", example = "55")
        Long plannerId,

        @Schema(description = "플래너 날짜", example = "2025-11-10")
        LocalDate date,

        @Schema(description = "불꽃(연속성) 여부", example = "true")
        boolean flame,

        @Schema(description = "사용자 피드백 (예: GOOD, NORMAL, BAD)", example = "GOOD")
        PlannerFeedback feedback,

        @Schema(description = "플래너 항목 목록")
        List<PlannerItemDto> items,

        @Schema(description = "플래너 생성 시각", example = "2025-11-10T09:00:00")
        LocalDateTime createdAt,

        @Schema(description = "플래너 수정 시각", example = "2025-11-10T09:15:00")
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