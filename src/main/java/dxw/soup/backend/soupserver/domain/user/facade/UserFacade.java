package dxw.soup.backend.soupserver.domain.user.facade;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.question.service.QuestionService;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserNicknameUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserSignupRequest;
import dxw.soup.backend.soupserver.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final QuestionService questionService;

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
}
