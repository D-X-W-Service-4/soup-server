package dxw.soup.backend.soupserver.domain.user.controller;

import dxw.soup.backend.soupserver.domain.user.dto.request.UserNicknameUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserSignupRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserInfoResponse;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "사용자")
public interface UserApi {

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> signUp(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserSignupRequest request
    );

    @Operation(summary = "닉네임 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> updateNickname(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserNicknameUpdateRequest request
    );

    @Operation(summary = "사용자 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal UserPrincipal principal
    );

    @Operation(summary = "사용자 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    CommonResponse<?> updateUserInfo(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserUpdateRequest request
    );
}
