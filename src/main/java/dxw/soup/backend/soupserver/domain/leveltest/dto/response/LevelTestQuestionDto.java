package dxw.soup.backend.soupserver.domain.leveltest.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import lombok.Builder;

@Builder
public record LevelTestQuestionDto(
        Long levelTestQuestionId,
        Integer questionNumber,
        QuestionDto question,
        boolean isCorrect,
        String userAnswer,
        String descriptiveImageUrl,
        boolean isTimeout,
        Integer essayTypeScore,
        String essayTypeScoreText
) {
    public static LevelTestQuestionDto from(LevelTestQuestion levelTestQuestion) {
        return LevelTestQuestionDto.builder()
                .levelTestQuestionId(levelTestQuestion.getId())
                .questionNumber(levelTestQuestion.getQuestionNumber())
                .question(QuestionDto.from(levelTestQuestion.getQuestion()))
                .isCorrect(levelTestQuestion.isCorrect())
                .userAnswer(levelTestQuestion.getUserAnswer())
                .descriptiveImageUrl(levelTestQuestion.getDescriptiveImageUrl())
                .isTimeout(levelTestQuestion.isTimeout())
                .essayTypeScore(levelTestQuestion.getEssayTypeScore())
                .essayTypeScoreText(levelTestQuestion.getEssayTypeScoreText())
                .build();
    }
}
