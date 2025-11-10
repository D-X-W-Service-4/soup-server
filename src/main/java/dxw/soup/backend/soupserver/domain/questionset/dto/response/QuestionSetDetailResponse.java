package dxw.soup.backend.soupserver.domain.questionset.dto.response;

import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestQuestionDto;
import dxw.soup.backend.soupserver.domain.leveltest.entity.LevelTest;
import dxw.soup.backend.soupserver.domain.question.dto.response.SubjectUnitDto;
import dxw.soup.backend.soupserver.domain.questionset.entity.QuestionSet;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "문제풀이 상세 응답 DTO")
public record QuestionSetDetailResponse(

        @Schema(description = "문제풀이 ID", example = "42")
        Long questionSetId,

        @Schema(description = "전체 문항 수", example = "10")
        Integer totalQuestionCount,

        @Schema(description = "맞힌 문항 수", example = "8")
        Integer correctCount,

        @Schema(description = "문제풀이 완료 시각", example = "2025-11-10T10:30:00")
        LocalDateTime finishedAt,

        @Schema(description = "문항 정보 목록")
        List<QuestionSetItemDto> questionSetItems,

        @Schema(description = "테스트 생성 시각", example = "2025-11-10T09:00:00")
        LocalDateTime createdAt,

        @Schema(description = "테스트 수정 시각", example = "2025-11-10T09:15:00")
        LocalDateTime updatedAt
) {
    public static QuestionSetDetailResponse of(
            QuestionSet questionSet,
            List<QuestionSetItemDto> questionSetItems
    ) {
        return QuestionSetDetailResponse.builder()
                .questionSetId(questionSet.getId())
                .totalQuestionCount(questionSet.getTotalQuestionCount())
                .correctCount(questionSet.getCorrectCount())
                .finishedAt(questionSet.getFinishedAt())
                .questionSetItems(questionSetItems)
                .createdAt(questionSet.getCreatedAt())
                .updatedAt(questionSet.getUpdatedAt())
                .build();
    }
}