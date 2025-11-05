package dxw.soup.backend.soupserver.domain.model.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "model-service", url = "${external.api-url.model}")
public interface ModelClient {
    @GetMapping
    ResponseEntity<String> getHealthCheck();
}
