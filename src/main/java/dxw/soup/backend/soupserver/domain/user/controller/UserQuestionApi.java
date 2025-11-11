package dxw.soup.backend.soupserver.domain.user.controller;

import dxw.soup.backend.soupserver.domain.user.dto.response.UserQuestionFindAllResponse;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import dxw.soup.backend.soupserver.domain.user.enums.UserQuestionFilter;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "문제 조회")
public interface UserQuestionApi {

    @Operation(summary = "사용자가 풀었던 문제 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<UserQuestionFindAllResponse> getAllQuestions(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam(required = false) UserQuestionFilter filter,
            @RequestParam(required = false) Grade grade,
            @RequestParam(required = false) Integer term,
            @RequestParam(required = false) Long subjectUnitId
    );
}
