package dxw.soup.backend.soupserver.domain.planner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "플래너 생성 요청 DTO")
public record PlannerCreateRequest(

        @NotNull
        @Schema(description = "플래너 날짜", example = "2025-11-10")
        LocalDate date
) {
}