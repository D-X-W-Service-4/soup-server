package dxw.soup.backend.soupserver.domain.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record GenerateLevelTestRequest(
        @JsonProperty("soup_level")
        String soupLevel,

        String workbook,

        @JsonProperty("unit_list")
        Map<Long, String> unitList
) {
    public static GenerateLevelTestRequest of(
            String soupLevel,
            String workbook,
            Map<Long, String> unitList
    ) {
        return new GenerateLevelTestRequest(soupLevel, workbook, unitList);
    }
}
