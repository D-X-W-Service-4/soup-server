package dxw.soup.backend.soupserver.domain.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;

@Builder
public record GenerateLevelTestRequest(
        @JsonProperty("soup_level")
        String soupLevel,

        String workbooks,

        @JsonProperty("unit_list")
        Map<String, String> unitList
) {
    public static GenerateLevelTestRequest of(
            String soupLevel,
            String workbooks,
            Map<String, String> unitList
    ) {
        return new GenerateLevelTestRequest(soupLevel, workbooks, unitList);
    }
}
