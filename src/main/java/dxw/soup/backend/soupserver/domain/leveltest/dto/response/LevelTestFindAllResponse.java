package dxw.soup.backend.soupserver.domain.leveltest.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public record LevelTestFindAllResponse(
        List<LevelTestDto> levelTests
) {
    public static LevelTestFindAllResponse of(List<LevelTest> levelTests) {
        return new LevelTestFindAllResponse(
                levelTests.stream().map(LevelTestDto::from).toList()
        );
    }

    @Builder
    public record LevelTestDto(
            Long levelTestId,
            Integer timeLimit,
            Integer totalQuestionCount,
            Integer correctCount,
            Integer score,
            Soup resultSoup,
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
