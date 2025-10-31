package dxw.soup.backend.soupserver.domain.leveltest.dto.request;

import java.util.List;

public record LevelTestCreateRequest(
        List<Long> subjectUnitIds
) {
}
