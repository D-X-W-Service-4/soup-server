package dxw.soup.backend.soupserver.domain.user.dto.response;

import dxw.soup.backend.soupserver.domain.user.entity.UserQuestion;
import java.util.List;

public record UserQuestionStarFindAllResponse(
        List<UserQuestionStarDto> stars
) {
    public static UserQuestionStarFindAllResponse of(List<UserQuestionStarDto> stars) {
        return new UserQuestionStarFindAllResponse(stars);
    }

    public record UserQuestionStarDto(
            String questionId,
            boolean isStarred
    ) {
        public static UserQuestionStarDto from(UserQuestion userQuestion) {
            return new UserQuestionStarDto(
                    userQuestion.getQuestion().getId(),
                    userQuestion.isStarred()
            );
        }

        public static UserQuestionStarDto from(String questionId, boolean isStarred) {
            return new UserQuestionStarDto(questionId, isStarred);
        }
    }
}
