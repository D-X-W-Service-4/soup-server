package dxw.soup.backend.soupserver.domain.leveltest.event;

import java.util.List;

public record LevelTestUpdateGradeResultEvent(
        Long userId,
        Long levelTestId,
        List<String> questionIds
) {
}
