package dxw.soup.backend.soupserver.domain.planner.dto.response;

import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.entity.PlannerItem;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record AIPlanResponse(
        Meta meta,
        List<ContentItem> content,

        Integer contentTotalMin
) {
        public record Meta(
                @JsonFormat(pattern = "yyyy-MM-dd")
                LocalDate date,

                String dayOfWeek,

                Integer plannedTimeMin
        ) {}

        public record ContentItem(
                String text,
                Double time
        ) {}

        public List<PlannerItem> toPlannerItems(Planner planner) {
                if (this.content == null) {
                        return List.of();
                }

                return content.stream()
                        .map(contentItem -> PlannerItem.builder()
                                .planner(planner)
                                .content(contentItem.text())
                                .duration(contentItem.time())
                                .checked(false)
                                .build())
                        .toList();
        }
}