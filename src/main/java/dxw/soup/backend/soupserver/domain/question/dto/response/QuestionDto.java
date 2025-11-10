package dxw.soup.backend.soupserver.domain.question.dto.response;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문항 요약 정보 DTO")
public record QuestionDto(

        @Schema(description = "문항 ID", example = "M1_1_01_00041_42236")
        String questionId
) {
    public static QuestionDto from(Question question) {
        return QuestionDto.builder()
                .questionId(question.getId())
                .build();
    }
}