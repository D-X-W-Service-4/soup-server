package dxw.soup.backend.soupserver.domain.user.dto.request;

import dxw.soup.backend.soupserver.domain.user.entity.User;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;

public record UserUpdateRequest(
        String nickname,
        Grade grade,
        Integer term
) {
}
