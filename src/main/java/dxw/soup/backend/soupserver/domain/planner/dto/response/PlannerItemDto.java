package dxw.soup.backend.soupserver.domain.planner.dto.response;

import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;

public record PlannerItemDto(
        Long plannerItemId,
        String content,
        boolean checked,
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