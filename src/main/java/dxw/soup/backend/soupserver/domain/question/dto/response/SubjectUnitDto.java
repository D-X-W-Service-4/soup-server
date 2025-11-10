package dxw.soup.backend.soupserver.domain.question.dto.response;

import dxw.soup.backend.soupserver.domain.question.entity.SubjectUnit;
import dxw.soup.backend.soupserver.domain.user.enums.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "과목 단원 정보 DTO")
public record SubjectUnitDto(

        @Schema(description = "단원 ID", example = "5")
        Long subjectUnitId,

        @Schema(description = "단원 이름", example = "정수와 유리수 - 정수와 유리수의 덧셈과 뺄셈")
        String name,

        @Schema(description = "학년", example = "M2")
        Grade grade,

        @Schema(description = "단원 번호", example = "1-1")
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