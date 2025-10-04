package dxw.soup.backend.soupserver.domain.user.dto.response;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import dxw.soup.backend.soupserver.global.common.entity.BaseTimeEntity;

import java.util.List;

public record UserInfoResponse (
        String email,
        String nickname,
        Grade grade,
        Integer term,
        Double studyHours,
        String workbooks,
        Soup soup,
        long solvedQuestionCount,
        long starredQuestionCount,
        Double plannerAchievementRate,
        Integer flameRunDateCount
){
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
