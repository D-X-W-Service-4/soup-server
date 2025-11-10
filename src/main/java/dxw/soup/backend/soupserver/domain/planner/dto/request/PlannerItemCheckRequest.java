package dxw.soup.backend.soupserver.domain.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "플래너 항목 체크 요청 DTO")
public record PlannerItemCheckRequest(

        @NotNull
        @Schema(description = "체크 여부", example = "true")
        Boolean checked
) {
}