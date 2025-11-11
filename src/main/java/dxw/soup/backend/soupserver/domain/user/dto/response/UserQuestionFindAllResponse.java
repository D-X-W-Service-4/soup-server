package dxw.soup.backend.soupserver.domain.user.dto.response;

import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(description = "사용자가 푼(또는 즐겨찾기한) 문제 목록 응답 DTO")
public record UserQuestionFindAllResponse(

        @Schema(description = "문항 목록")
        List<UserQuestionDto> questions
) {
    public static UserQuestionFindAllResponse of(List<UserQuestionDto> questions) {
        return new UserQuestionFindAllResponse(questions);
    }

    @Builder
    public record UserQuestionDto(
            Long userQuestionId,
            QuestionDto question,
            boolean answeredWrongBefore,
            boolean isStarred,
            Integer tryCount
    ) {
        public static UserQuestionDto from(UserQuestion userQuestion) {
            return UserQuestionDto.builder()
                    .userQuestionId(userQuestion.getId())
                    .question(QuestionDto.from(userQuestion.getQuestion()))
                    .answeredWrongBefore(userQuestion.isAnsweredWrongBefore())
                    .isStarred(userQuestion.isStarred())
                    .tryCount(userQuestion.getTryCount())
                    .build();
        }
    }
}