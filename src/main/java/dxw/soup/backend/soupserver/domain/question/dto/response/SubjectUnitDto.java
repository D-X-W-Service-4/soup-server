package dxw.soup.backend.soupserver.domain.question.dto.response;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import lombok.Builder;

@Builder
public record SubjectUnitDto(
        Long subjectUnitId,
        String name,
        Grade grade,
        String unitNumber
) {
    public static SubjectUnitDto from(SubjectUnit subjectUnit) {
        return SubjectUnitDto.builder()
                .subjectUnitId(subjectUnit.getId())
                .name(subjectUnit.getName())
                .grade(subjectUnit.getGrade())
                .unitNumber(subjectUnit.getUnitNumber())
                .build();
    }
}
