package dxw.soup.backend.soupserver.domain.questionset.dto.response;

import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSetItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제풀이 세트 내 개별 문항 응답 DTO")
public record QuestionSetItemDto(

        @Schema(description = "문제풀이 항목 ID", example = "501")
        Long questionSetItemId,

        @Schema(description = "문항 정보")
        QuestionDto question,

        @Schema(description = "정답 여부", example = "true")
        boolean isCorrect,

        @Schema(description = "사용자 입력 답변", example = "F = ma")
        String userAnswer,

        @Schema(description = "서술형 답변 이미지 URL (필요 시)", example = "https://example.com/uploads/answers/answer1.png")
        String descriptiveImageUrl,

        @Schema(description = "시간 초과 여부", example = "false")
        boolean isTimeout,

        @Schema(description = "서술형 문항 점수", example = "4")
        Integer essayTypeScore,

        @Schema(description = "서술형 문항 평가 코멘트", example = "핵심 개념은 이해했지만 일부 계산 과정이 부족합니다.")
        String essayTypeScoreText
) {
    public static QuestionSetItemDto from(QuestionSetItem questionSetItem) {
        return QuestionSetItemDto.builder()
                .questionSetItemId(questionSetItem.getId())
                .question(QuestionDto.from(questionSetItem.getQuestion()))
                .isCorrect(questionSetItem.isCorrect())
                .userAnswer(questionSetItem.getUserAnswer())
                .descriptiveImageUrl(questionSetItem.getDescriptiveImageUrl())
                .isTimeout(questionSetItem.isTimeout())
                .essayTypeScore(questionSetItem.getEssayTypeScore())
                .essayTypeScoreText(questionSetItem.getEssayTypeScoreText())
                .build();
    }
}