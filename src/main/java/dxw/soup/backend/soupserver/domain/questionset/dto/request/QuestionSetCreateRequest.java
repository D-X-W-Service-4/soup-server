package dxw.soup.backend.soupserver.domain.questionset.dto.request;

import java.util.List;

public record QuestionSetCreateRequest(
        List<String> questionIds
) {
}
