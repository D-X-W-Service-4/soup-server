package dxw.soup.backend.soupserver.domain.user.dto.request;

import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 수정 요청 DTO")
public record UserUpdateRequest(

        @Schema(description = "닉네임", example = "황수민")
        String nickname,

        @Schema(description = "학년", example = "M3")
        Grade grade,

        @Schema(description = "학기 (1 또는 2)", example = "2")
        Integer term
) {
}