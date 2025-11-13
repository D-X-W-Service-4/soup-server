package dxw.soup.backend.soupserver.domain.user.facade;

import dxw.soup.backend.soupserver.domain.planner.entity.Planner;
import dxw.soup.backend.soupserver.domain.planner.service.PlannerService;
import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserNicknameUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserSignupRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserInfoResponse;
import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Soup;
import dxw.soup.backend.soupserver.domain.user.repository.UserQuestionRepository;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final QuestionService questionService;
    private final PlannerService plannerService;

    @Transactional
    public void signUp(Long userId, UserSignupRequest request) {
        SubjectUnit lastSubjectUnit = questionService.findSubjectUnitById(request.lastSubjectUnitId());

        userService.signUp(
                userId,
                request.grade(),
                request.term(),
                lastSubjectUnit,
                request.studyHours(),
                request.workbooks()
        );
    }

    @Transactional
    public void updateNickname(Long userId, UserNicknameUpdateRequest request) {
        userService.updateNickname(userId, request.nickname());
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userService.findById(userId);
        long solvedQuestionCount = userService.getSolvedQuestionCount(user);
        long starredQuestionCount = userService.getStarredQuestionCount(user);
        Planner planner = plannerService.findByUserIdAndDateOrNull(user.getId(), LocalDate.now());
        double plannerAchievementRate = plannerService.getAchievementRate(planner);
        int flameRunDateCount = user.getFlameRunDateCount();

        return UserInfoResponse.of(
                user,
                solvedQuestionCount,
                starredQuestionCount,
                plannerAchievementRate,
                flameRunDateCount
        );
    }

    @Transactional
    public void updateUserInfo (Long userId, UserUpdateRequest request) {
        User user = userService.findById(userId);

        user.updateInfo(
                request.nickname(),
                request.grade(),
                request.term()
        );
    }

}
