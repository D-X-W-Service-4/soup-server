package dxw.soup.backend.soupserver.domain.questionset.controller;

import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetCreateRequest;
import dxw.soup.backend.soupserver.domain.questionset.dto.request.QuestionSetGradeRequest;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetDetailResponse;
import dxw.soup.backend.soupserver.domain.questionset.dto.response.QuestionSetFindAllResponse;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "문제풀이 (복습용)")
public interface QuestionSetApi {

    @Operation(summary = "문제풀이 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<QuestionSetDetailResponse> createQuestionSet(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody QuestionSetCreateRequest request
    );

    @Operation(summary = "문제풀이 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<QuestionSetFindAllResponse> getAllQuestionSets(
            @AuthenticationPrincipal UserPrincipal principal
    );

    @Operation(summary = "문제풀이 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<QuestionSetDetailResponse> getQuestionSetById(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long questionSetId
    );

    @Operation(summary = "문제풀이 채점")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> gradeQuestionSet(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long questionSetId,
            @RequestBody QuestionSetGradeRequest request
    );
}
