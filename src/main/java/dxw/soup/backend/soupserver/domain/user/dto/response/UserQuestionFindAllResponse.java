package dxw.soup.backend.soupserver.domain.user.dto.response;

import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import java.util.List;

public record UserQuestionFindAllResponse(
        List<QuestionDto> questions
) {
    public static UserQuestionFindAllResponse of(List<QuestionDto> questions) {
        return new UserQuestionFindAllResponse(questions);
    }
}
