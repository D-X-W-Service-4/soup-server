package dxw.soup.backend.soupserver.domain.user.controller;

import dxw.soup.backend.soupserver.domain.user.dto.request.UserNicknameUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserSignupRequest;
import dxw.soup.backend.soupserver.domain.user.dto.request.UserUpdateRequest;
import dxw.soup.backend.soupserver.domain.user.dto.response.UserInfoResponse;
import dxw.soup.backend.soupserver.domain.user.facade.UserFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestApiController("v1/users")
public class UserController {
    private final UserFacade userFacade;

    @PostMapping("/sign-up")
    public CommonResponse<?> signUp(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserSignupRequest request
    ) {
        userFacade.signUp(principal.getUserId(), request);
        return CommonResponse.ok();
    }

    @PatchMapping("/me/nickname")
    public CommonResponse<?> updateNickname(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserNicknameUpdateRequest request
    ) {
        userFacade.updateNickname(principal.getUserId(), request);
        return CommonResponse.ok();
    }

    @GetMapping("/me")
    public CommonResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        UserInfoResponse userInfo = userFacade.getUserInfo(principal.getUserId());
        return CommonResponse.ok(userInfo);
    }
    @PutMapping("/me")
    public CommonResponse<?> updateUserInfo(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody UserUpdateRequest request
    ) {
        userFacade.updateUserInfo(principal.getUserId(), request);
        return CommonResponse.ok();
    }
}
