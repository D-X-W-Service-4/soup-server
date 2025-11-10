package dxw.soup.backend.soupserver.domain.planner.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "플래너 불꽃(연속 기록) 정보 DTO")
public record PlannerFlameItem(

        @Schema(description = "플래너 날짜", example = "2025-11-10")
        LocalDate date,

        @Schema(description = "해당 날짜에 불꽃(연속성)이 있는지 여부", example = "true")
        boolean flame
) {
    public static PlannerFlameItem of(LocalDate date, boolean flame) {
        return new PlannerFlameItem(date, flame);
    }
}