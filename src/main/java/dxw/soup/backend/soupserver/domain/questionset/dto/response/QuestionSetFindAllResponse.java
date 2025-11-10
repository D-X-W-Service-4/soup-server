package dxw.soup.backend.soupserver.domain.questionset.dto.response;

import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public record QuestionSetFindAllResponse(
        List<QuestionSetDto> questionSets
) {
    public static QuestionSetFindAllResponse of(List<QuestionSetDto> questionSetDtos) {
        return new QuestionSetFindAllResponse(questionSetDtos);
    }

    @Builder
    @Schema(description = "문제풀이 요약 정보 DTO")
    public record QuestionSetDto(

            @Schema(description = "문제풀이 ID", example = "15")
            Long questionSetId,

            @Schema(description = "전체 문항 수", example = "10")
            Integer totalQuestionCount,

            @Schema(description = "맞힌 문항 수", example = "8")
            Integer correctCount,

            @Schema(description = "테스트 완료 시각", example = "2025-11-10T10:30:00")
            LocalDateTime finishedAt
    ) {
        public static QuestionSetDto from(QuestionSet questionSet) {
            return QuestionSetDto.builder()
                    .questionSetId(questionSet.getId())
                    .totalQuestionCount(questionSet.getTotalQuestionCount())
                    .correctCount(questionSet.getCorrectCount())
                    .finishedAt(questionSet.getFinishedAt())
                    .build();
        }
    }
}
