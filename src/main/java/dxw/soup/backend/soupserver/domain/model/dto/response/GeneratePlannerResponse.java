package dxw.soup.backend.soupserver.domain.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record GeneratePlannerResponse(
        PlannerMeta meta,

        List<PlannerContent> content,

        @JsonProperty("content_total_min")
        Integer contentTotalMin
) {
    public List<PlannerItem> toPlannerItems(Planner planner) {
        return content.stream()
                .map(plannerContent -> PlannerItem.builder()
                        .planner(planner)
                        .content(plannerContent.text() != null ? plannerContent.text() : "")
                        .duration(plannerContent.time() != null ? plannerContent.time().doubleValue() : null)
                        .checked(false)
                        .build())
                .collect(Collectors.toList());
    }

    @Builder
    public record PlannerMeta(
            String date,

            @JsonProperty("day_of_week")
            String dayOfWeek,

            @JsonProperty("planned_time_min")
            Integer plannedTimeMin
    ) {
    }

    @Builder
    public record PlannerContent(
            String text,

            Integer time
    ) {
    }
}
