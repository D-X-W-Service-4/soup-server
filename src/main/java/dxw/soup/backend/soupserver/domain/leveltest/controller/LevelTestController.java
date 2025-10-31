package dxw.soup.backend.soupserver.domain.leveltest.controller;

import dxw.soup.backend.soupserver.domain.leveltest.dto.request.LevelTestCreateRequest;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestDetailResponse;
import dxw.soup.backend.soupserver.domain.leveltest.dto.response.LevelTestFindAllResponse;
import dxw.soup.backend.soupserver.domain.leveltest.facade.LevelTestFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.auth.UserPrincipal;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestApiController("/v1/level-tests")
public class LevelTestController {
    private final LevelTestFacade levelTestFacade;

    @GetMapping
    public CommonResponse<LevelTestFindAllResponse> getAllLevelTests(@AuthenticationPrincipal UserPrincipal principal) {
        return CommonResponse.ok(levelTestFacade.getAllLevelTests(principal.getUserId()));
    }

    @GetMapping("/{levelTestId}")
    public CommonResponse<LevelTestDetailResponse> getLevelTestById(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long levelTestId
    ) {
        return CommonResponse.ok(levelTestFacade.getLevelTestById(principal.getUserId(), levelTestId));
    }

    @PostMapping
    public CommonResponse<LevelTestDetailResponse> createLevelTest(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestBody LevelTestCreateRequest request
    ) {

    }
}
