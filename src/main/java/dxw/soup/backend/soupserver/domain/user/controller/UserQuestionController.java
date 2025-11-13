package dxw.soup.backend.soupserver.domain.user.controller;

import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionFindAllResponse;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionStarFindAllResponse;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.domain.user.facade.UserQuestionFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiController("/v1/questions")
@RequiredArgsConstructor
public class UserQuestionController implements UserQuestionApi {
    private final UserQuestionFacade userQuestionFacade;

    @GetMapping
    public CommonResponse<UserQuestionFindAllResponse> getAllQuestions(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) UserQuestionFilter filter,
            @RequestParam(required = false) Grade grade,
            @RequestParam(required = false) Integer term,
            @RequestParam(required = false) Long subjectUnitId
    ) {
        return CommonResponse.ok(userQuestionFacade.getAllQuestions(principal.getUserId(), filter, grade, term, subjectUnitId));
    }

    @PatchMapping("/{questionId}/star")
    public CommonResponse<?> starQuestion(@AuthenticationPrincipal UserPrincipal principal, @PathVariable String questionId) {
        userQuestionFacade.starQuestion(principal.getUserId(), questionId);
        return CommonResponse.ok();
    }

    @GetMapping("/star")
    public CommonResponse<UserQuestionStarFindAllResponse> getQuestionStar(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam String[] questionIds
    ) {
        return CommonResponse.ok(userQuestionFacade.getQuestionStar(principal.getUserId(), questionIds));
    }

}
