package dxw.soup.backend.soupserver.domain.leveltest.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.question.dto.response.SubjectUnitDto;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "레벨 테스트 상세 응답 DTO")
public record LevelTestDetailResponse(

        @Schema(description = "레벨 테스트 ID", example = "42")
        Long levelTestId,

        @Schema(description = "테스트 제한 시간 (분 단위)", example = "30")
        Integer timeLimit,

        @Schema(description = "전체 문항 수", example = "10")
        Integer totalQuestionCount,

        @Schema(description = "맞힌 문항 수", example = "8")
        Integer correctCount,

        @Schema(description = "테스트 완료 시각", example = "2025-11-10T10:30:00")
        LocalDateTime finishedAt,

        @Schema(description = "최종 점수", example = "80")
        Integer score,

        @Schema(description = "결과 수프 타입", example = "MISO")
        Soup resultSoup,

        @Schema(description = "문항별 과목 단위 정보 목록")
        List<SubjectUnitDto> subjectUnits,

        @Schema(description = "레벨 테스트 문항 정보 목록")
        List<LevelTestQuestionDto> levelTestQuestions,

        @Schema(description = "테스트 생성 시각", example = "2025-11-10T09:00:00")
        LocalDateTime createdAt,

        @Schema(description = "테스트 수정 시각", example = "2025-11-10T09:15:00")
        LocalDateTime updatedAt
) {
    public static LevelTestDetailResponse of(
            LevelTest levelTest,
            List<LevelTestQuestionDto> levelTestQuestions,
            List<SubjectUnitDto> subjectUnits
    ) {
        return LevelTestDetailResponse.builder()
                .levelTestId(levelTest.getId())
                .timeLimit(levelTest.getTimeLimit())
                .totalQuestionCount(levelTest.getTotalQuestionCount())
                .correctCount(levelTest.getCorrectCount())
                .finishedAt(levelTest.getFinishedAt())
                .score(levelTest.getScore())
                .resultSoup(levelTest.getResultSoup())
                .subjectUnits(subjectUnits)
                .levelTestQuestions(levelTestQuestions)
                .createdAt(levelTest.getCreatedAt())
                .updatedAt(levelTest.getUpdatedAt())
                .build();
    }
}