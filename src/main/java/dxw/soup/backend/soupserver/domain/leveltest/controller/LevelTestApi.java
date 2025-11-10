package dxw.soup.backend.soupserver.domain.leveltest.controller;

import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestCreateRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestGradeRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestDetailResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestFindAllResponse;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "수준테스트")
public interface LevelTestApi {

    @Operation(summary = "수준테스트 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<LevelTestFindAllResponse> getAllLevelTests(@AuthenticationPrincipal UserPrincipal principal);

    @Operation(summary = "수준테스트 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<LevelTestDetailResponse> getLevelTestById(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long levelTestId
    );

    @Operation(summary = "수준테스트 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<LevelTestDetailResponse> createLevelTest(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody LevelTestCreateRequest request
    );

    @Operation(summary = "수준테스트 채점")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> gradeLevelTest(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long levelTestId,
            @RequestBody LevelTestGradeRequest request
    );
}
