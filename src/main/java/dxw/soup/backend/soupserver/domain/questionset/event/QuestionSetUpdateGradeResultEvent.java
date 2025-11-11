package dxw.soup.backend.soupserver.domain.questionset.event;

public record QuestionSetUpdateGradeResultEvent(
        Long userId,
        Long questionSetId
) {
}
