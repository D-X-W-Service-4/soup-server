package dxw.soup.backend.soupserver.domain.leveltest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "수준테스트 채점 요청 DTO")
public record LevelTestGradeRequest(

        @Schema(description = "사용자의 각 문항별 답변 목록")
        List<QuestionUserAnswer> answers
) {

    @Schema(description = "사용자의 개별 문항 응답 정보")
    public record QuestionUserAnswer(

            @Schema(description = "문항 ID", example = "M1_1_01_00041_42236")
            String questionId,

            @Schema(description = "사용자 입력 답변", example = "1번")
            String userAnswer,

            @Schema(description = "서술형 답변 이미지 URL (필요시)", example = "https://example.com/uploads/answers/12345.png")
            String descriptiveImageUrl
    ) {
    }
}