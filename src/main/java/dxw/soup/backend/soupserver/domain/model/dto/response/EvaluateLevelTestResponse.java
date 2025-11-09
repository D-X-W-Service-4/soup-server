package dxw.soup.backend.soupserver.domain.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record EvaluateLevelTestResponse(
        @JsonProperty("evaluate_result")
        List<EvaluationResult> results
) {
    @Builder
    public record EvaluationResult(
            Integer score,

            @JsonProperty("user_answer")
            String userAnswer,

            @JsonProperty("is_correct")
            Boolean isCorrect,

            @JsonProperty("essay_type_score_text")
            String essayTypeScoreText
    ) {
    }
}
