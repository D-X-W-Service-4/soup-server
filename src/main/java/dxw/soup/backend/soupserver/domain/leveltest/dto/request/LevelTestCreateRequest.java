package dxw.soup.backend.soupserver.domain.leveltest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record LevelTestCreateRequest(
        @Schema(description = "첫 수준테스트 실행 여부")
        boolean isInitialTest,
        @Schema(description = "단원 ID 목록", example = "[1, 2, 3]")
        List<Long> subjectUnitIds
) {
}
