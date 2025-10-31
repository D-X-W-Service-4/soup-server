package dxw.soup.backend.soupserver.domain.leveltest.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.question.dto.response.SubjectUnitDto;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record LevelTestDetailResponse(
        Long levelTestId,
        Integer timeLimit,
        Integer totalQuestionCount,
        Integer correctCount,
        LocalDateTime startedAt,
        LocalDateTime finishedAt,
        Integer score,
        Soup resultSoup,
        List<SubjectUnitDto> subjectUnits,
        List<LevelTestQuestionDto> levelTestQuestions,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static LevelTestDetailResponse of(LevelTest levelTest, List<LevelTestQuestionDto> levelTestQuestions, List<SubjectUnitDto> subjectUnits) {
        return LevelTestDetailResponse.builder()
                .levelTestId(levelTest.getId())
                .timeLimit(levelTest.getTimeLimit())
                .totalQuestionCount(levelTest.getTotalQuestionCount())
                .correctCount(levelTest.getCorrectCount())
                .startedAt(levelTest.getStartedAt())
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
