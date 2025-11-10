package dxw.soup.backend.soupserver.domain.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "플래너 불꽃(연속성) 목록 응답 DTO")
public record PlannerFlameResponse(

        @Schema(description = "플래너 불꽃(연속성) 정보 목록")
        List<PlannerFlameItem> flames
) {
    public static PlannerFlameResponse of(List<PlannerFlameItem> flames) {
        return new PlannerFlameResponse(flames);
    }
}