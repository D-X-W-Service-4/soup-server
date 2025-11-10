package dxw.soup.backend.soupserver.domain.user.dto.response;

import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 응답 DTO")
public record UserInfoResponse(

        @Schema(description = "이메일", example = "soup@example.com")
        String email,

        @Schema(description = "닉네임", example = "황수민")
        String nickname,

        @Schema(description = "학년", example = "M2")
        Grade grade,

        @Schema(description = "학기 (1 또는 2)", example = "1")
        Integer term,

        @Schema(description = "누적 학습 시간(시간 단위)", example = "48.5")
        Double studyHours,

        @Schema(description = "사용 중인 워크북 목록 (쉼표로 구분된 문자열)", example = "개념 쎈,개념원리")
        String workbooks,

        @Schema(description = "결과 수프 타입", example = "TOMATO")
        Soup soup,

        @Schema(description = "총 푼 문제 수", example = "150")
        long solvedQuestionCount,

        @Schema(description = "별표한 문제 수", example = "12")
        long starredQuestionCount,

        @Schema(description = "플래너 달성률 (%)", example = "87.5")
        Double plannerAchievementRate,

        @Schema(description = "연속 플래너 수행 일수", example = "9")
        Integer flameRunDateCount
) {
    public static UserInfoResponse of(
            User user,
            long solvedQuestionCount,
            long starredQuestionCount,
            double plannerAchievementRate,
            int flameRunDateCount
    ) {
        return new UserInfoResponse(
                user.getEmail(),
                user.getNickname(),
                user.getGrade(),
                user.getTerm(),
                user.getStudyHours(),
                user.getWorkbooks(),
                user.getSoup(),
                solvedQuestionCount,
                starredQuestionCount,
                plannerAchievementRate,
                flameRunDateCount
        );
    }
}