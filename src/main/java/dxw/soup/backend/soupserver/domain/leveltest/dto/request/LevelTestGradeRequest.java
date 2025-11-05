package dxw.soup.backend.soupserver.domain.leveltest.dto.request;

import java.util.List;

public record LevelTestGradeRequest(
        List<QuestionUserAnswer> answers
) {
    public record QuestionUserAnswer(
            String questionId,
            String userAnswer,
            String descriptiveImageUrl
    ) {

    }
}
