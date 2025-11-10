package dxw.soup.backend.soupserver.domain.planner.dto.response;

import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "플래너 항목 DTO")
public record PlannerItemDto(

        @Schema(description = "플래너 항목 ID", example = "101")
        Long plannerItemId,

        @Schema(description = "플래너 항목 내용", example = "알고리즘 문제 2개 풀기")
        String content,

        @Schema(description = "체크 여부", example = "true")
        boolean checked,

        @Schema(description = "소요 시간(시간 단위)", example = "1.5")
        Double duration
) {
    public static PlannerItemDto from(PlannerItem plannerItem) {
        return new PlannerItemDto(
                plannerItem.getId(),
                plannerItem.getContent(),
                plannerItem.isChecked(),
                plannerItem.getDuration()
        );
    }
}