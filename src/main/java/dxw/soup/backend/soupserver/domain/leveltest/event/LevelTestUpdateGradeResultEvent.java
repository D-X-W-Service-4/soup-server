package dxw.soup.backend.soupserver.domain.leveltest.event;

public record LevelTestUpdateGradeResultEvent(
        Long userId,
        Long levelTestId
) {
}
