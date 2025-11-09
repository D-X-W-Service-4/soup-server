package dxw.soup.backend.soupserver.domain.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Comparator;
import lombok.Builder;

import java.util.List;

@Builder
public record GenerateLevelTestResponse(
        @JsonProperty("level_test") List<LevelTestQuestionItem> levelTest
) {
    public List<String> mapQuestionIds() {
        return levelTest.stream()
                .sorted(Comparator.comparing(LevelTestQuestionItem::questionNumber))
                .map(LevelTestQuestionItem::questionId)
                .toList();
    }

    @Builder
    public record LevelTestQuestionItem(
            @JsonProperty("question_id")
            String questionId,

            @JsonProperty("question_number")
            Integer questionNumber,

            @JsonProperty("question_text")
            String questionText,

            String topic,

            Double time,

            String difficulty,

            @JsonProperty("question_format")
            String questionFormat
    ) {
    }
}
