package dxw.soup.backend.soupserver.domain.model.external;

import dxw.soup.backend.soupserver.domain.model.dto.request.EvaluateLevelTestRequest;
import dxw.soup.backend.soupserver.domain.model.dto.request.GenerateLevelTestRequest;
import dxw.soup.backend.soupserver.domain.model.dto.request.GeneratePlannerRequest;
import dxw.soup.backend.soupserver.domain.model.dto.response.EvaluateLevelTestResponse;
import dxw.soup.backend.soupserver.domain.model.dto.response.GenerateLevelTestResponse;
import dxw.soup.backend.soupserver.domain.model.dto.response.GeneratePlannerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "model-service", url = "${external.api-url.model}")
public interface ModelClient {
    @GetMapping
    ResponseEntity<String> getHealthCheck();

    @PostMapping("/v1/planner/generate")
    ResponseEntity<GeneratePlannerResponse> generatePlanner(@RequestBody GeneratePlannerRequest request);

    @PostMapping("/v1/level-test/generate")
    ResponseEntity<GenerateLevelTestResponse> generateLevelTest(@RequestBody GenerateLevelTestRequest request);

    @PostMapping("/v1/level-test/evaluate")
    ResponseEntity<EvaluateLevelTestResponse> evaluateEssayLevelTest(@RequestBody EvaluateLevelTestRequest request);
}
