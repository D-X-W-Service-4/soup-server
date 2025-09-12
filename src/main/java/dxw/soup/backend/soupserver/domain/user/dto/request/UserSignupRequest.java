package dxw.soup.backend.soupserver.domain.user.dto.request;

import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import java.util.List;

public record UserSignupRequest(
        Grade grade,
        Integer term,
        Long lastSubjectUnitId,
        Double studyHours,
        List<String> workbooks
) {
}
