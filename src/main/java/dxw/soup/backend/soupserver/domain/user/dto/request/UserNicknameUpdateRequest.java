package dxw.soup.backend.soupserver.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 닉네임 수정 요청 DTO")
public record UserNicknameUpdateRequest(

        @Schema(description = "새 닉네임", example = "황수민")
        String nickname
) {
}