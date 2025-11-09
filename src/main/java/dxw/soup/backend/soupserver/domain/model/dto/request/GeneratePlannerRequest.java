package dxw.soup.backend.soupserver.domain.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GeneratePlannerRequest(
        @JsonProperty("student_id")
        Long studentId,

        String date
) {
    public static GeneratePlannerRequest of(Long studentId, String date) {
        return new GeneratePlannerRequest(studentId, date);
    }
}
