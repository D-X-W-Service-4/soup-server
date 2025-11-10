package dxw.soup.backend.soupserver.domain.questionset.controller;

import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetCreateRequest;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetDetailResponse;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetFindAllResponse;
import dxw.soup.backend.soupserver.domain.questionset.facade.QuestionSetFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiController("/v1/question-sets")
@RequiredArgsConstructor
public class QuestionSetController implements QuestionSetApi {

    private final QuestionSetFacade questionSetFacade;

    @PostMapping
    public CommonResponse<QuestionSetDetailResponse> createQuestionSet(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody QuestionSetCreateRequest request
    ) {
        return CommonResponse.ok(questionSetFacade.createQuestionSet(principal.getUserId(), request));
    }

    @GetMapping
    public CommonResponse<QuestionSetFindAllResponse> getAllQuestionSets(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return CommonResponse.ok(questionSetFacade.getAllQuestionSetsByUser(principal.getUserId()));
    }

    @GetMapping("/{questionSetId}")
    public CommonResponse<QuestionSetDetailResponse> getQuestionSetById(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long questionSetId
    ) {
        return CommonResponse.ok(questionSetFacade.getQuestionSetById(principal.getUserId(), questionSetId));
    }

    @PostMapping("/{questionSetId}/grade")
    public CommonResponse<?> gradeQuestionSet(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long questionSetId
    ) {
        //TODO: 문제풀이 채점 API 구현
        return CommonResponse.ok();
    }
}
