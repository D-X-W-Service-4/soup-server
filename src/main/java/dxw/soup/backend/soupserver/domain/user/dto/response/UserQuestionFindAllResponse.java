package dxw.soup.backend.soupserver.domain.user.dto.response;

import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "사용자가 푼(또는 즐겨찾기한) 문제 목록 응답 DTO")
public record UserQuestionFindAllResponse(

        @Schema(description = "문항 목록")
        List<QuestionDto> questions
) {
    public static UserQuestionFindAllResponse of(List<QuestionDto> questions) {
        return new UserQuestionFindAllResponse(questions);
    }
}