package dxw.soup.backend.soupserver.domain.user.controller;

import dxw.soup.backend.soupserver.domain.question.dto.response.QuestionDto;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.domain.user.facade.UserQuestionFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiController("/v1/questions")
@RequiredArgsConstructor
public class UserQuestionController {
    private final UserQuestionFacade userQuestionFacade;

    @GetMapping
    public CommonResponse<QuestionDto> getAllQuestions(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) UserQuestionFilter filter,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) Long subjectUnitId
    ) {
        return CommonResponse.ok(userQuestionFacade.getAllQuestions(principal.getUserId(), filter, semester, subjectUnitId));
    }
}
