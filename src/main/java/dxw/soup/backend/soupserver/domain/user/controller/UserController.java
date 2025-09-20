package dxw.soup.backend.soupserver.domain.user.controller;

import dxw.soup.backend.soupserver.domain.user.dto.request.UserNicknameUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserSignupRequest;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserInfoResponse;
import dxw.soup.backend.soupserver.domain.user.facade.UserFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestApiController("v1/users")
public class UserController {
    private final UserFacade userFacade;

    @PostMapping("/sign-up")
    public ApiResponse<?> signUp(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserSignupRequest request
    ) {
        userFacade.signUp(principal.getUserId(), request);
        return ApiResponse.ok();
    }

    @PatchMapping("/me/nickname")
    public ApiResponse<?> updateNickname(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserNicknameUpdateRequest request
    ) {
        userFacade.updateNickname(principal.getUserId(), request);
        return ApiResponse.ok();
    }

    @GetMapping("/me")
    public ApiResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        UserInfoResponse userInfo = userFacade.getUserInfo(principal.getUserId());
        return ApiResponse.ok(userInfo);
    }
}
