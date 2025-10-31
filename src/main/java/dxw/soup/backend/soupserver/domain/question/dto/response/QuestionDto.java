package dxw.soup.backend.soupserver.domain.question.dto.response;

import dxw.soup.backend.soupserver.domain.question.entity.Question;
import lombok.Builder;

@Builder
public record QuestionDto(
        String questionId
) {
    public static QuestionDto from(Question question) {
        return QuestionDto.builder()
                .questionId(question.getId())
                .build();
    }
}
