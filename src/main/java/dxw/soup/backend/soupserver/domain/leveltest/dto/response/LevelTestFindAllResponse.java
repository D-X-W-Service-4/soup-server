package dxw.soup.backend.soupserver.domain.leveltest.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Schema(description = "수준테스트 전체 조회 응답 DTO")
public record LevelTestFindAllResponse(

        @Schema(description = "수준테스트 목록")
        List<LevelTestDto> levelTests
) {
    public static LevelTestFindAllResponse of(List<LevelTest> levelTests) {
        return new LevelTestFindAllResponse(
                levelTests.stream().map(LevelTestDto::from).toList()
        );
    }

    @Builder
    @Schema(description = "수준테스트 요약 정보 DTO")
    public record LevelTestDto(

            @Schema(description = "수준테스트 ID", example = "15")
            Long levelTestId,

            @Schema(description = "테스트 제한 시간 (분 단위)", example = "30")
            Integer timeLimit,

            @Schema(description = "전체 문항 수", example = "10")
            Integer totalQuestionCount,

            @Schema(description = "맞힌 문항 수", example = "8")
            Integer correctCount,

            @Schema(description = "최종 점수", example = "80")
            Integer score,

            @Schema(description = "결과 수프 타입", example = "TOMATO")
            Soup resultSoup,

            @Schema(description = "테스트 완료 시각", example = "2025-11-10T10:30:00")
            LocalDateTime finishedAt
    ) {
        public static LevelTestDto from(LevelTest levelTest) {
            return LevelTestDto.builder()
                    .levelTestId(levelTest.getId())
                    .timeLimit(levelTest.getTimeLimit())
                    .totalQuestionCount(levelTest.getTotalQuestionCount())
                    .correctCount(levelTest.getCorrectCount())
                    .score(levelTest.getScore())
                    .resultSoup(levelTest.getResultSoup())
                    .finishedAt(levelTest.getFinishedAt())
                    .build();
        }
    }
}