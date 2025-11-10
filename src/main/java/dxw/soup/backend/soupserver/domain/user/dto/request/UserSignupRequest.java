package dxw.soup.backend.soupserver.domain.user.dto.request;

import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "사용자 회원가입 요청 DTO")
public record UserSignupRequest(

        @Schema(description = "사용자 학년", example = "M2")
        Grade grade,

        @Schema(description = "학기 (1 또는 2)", example = "1")
        Integer term,

        @Schema(description = "마지막 학습한 단원 ID", example = "3")
        Long lastSubjectUnitId,

        @Schema(description = "누적 학습 시간(시간 단위)", example = "25.5")
        Double studyHours,

        @Schema(description = "사용 중인 워크북 ID 또는 이름 목록", example = "[\"개념 쎈\", \"개념원리\"]")
        List<String> workbooks
) {
}