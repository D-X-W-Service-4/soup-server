package dxw.soup.backend.soupserver.domain.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record EvaluateLevelTestRequest(
        @JsonProperty("level_test_result")
        List<LevelTestResultItem> levelTestResult
) {
    public static EvaluateLevelTestRequest of(List<LevelTestResultItem> levelTestResult) {
        return new EvaluateLevelTestRequest(levelTestResult);
    }

    @Builder
    public record LevelTestResultItem(
            @JsonProperty("user_answer_image")
            String userAnswerImage,

            String text,

            String answer,

            @JsonProperty("answer_text")
            String answerText,

            String topic
    ) {
        public static LevelTestResultItem of(
                String userAnswerImage,
                String text,
                String answer,
                String answerText,
                String topic
        ) {
            return new LevelTestResultItem(userAnswerImage, text, answer, answerText, topic);
        }
    }
}
