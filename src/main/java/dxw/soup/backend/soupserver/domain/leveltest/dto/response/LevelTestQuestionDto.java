package dxw.soup.backend.soupserver.domain.leveltest.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTestQuestion;
import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "수준테스트 문항 응답 DTO")
public record LevelTestQuestionDto(

        @Schema(description = "수준테스트 문항 ID", example = "101")
        Long levelTestQuestionId,

        @Schema(description = "문항 번호 (순서)", example = "3")
        Integer questionNumber,

        @Schema(description = "문항 상세 정보")
        QuestionDto question,

        @Schema(description = "정답 여부")
        boolean isCorrect,

        @Schema(description = "사용자 입력 답변", example = "F = ma")
        String userAnswer,

        @Schema(description = "서술형 답변 이미지 URL (필요 시)", example = "https://example.com/uploads/answers/12345.png")
        String descriptiveImageUrl,

        @Schema(description = "시간 초과 여부")
        boolean isTimeout,

        @Schema(description = "서술형 문항 점수 (자동 채점 결과 등)", example = "5")
        Integer essayTypeScore,

        @Schema(description = "서술형 점수 평가 코멘트", example = "핵심 개념을 잘 설명했습니다.")
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