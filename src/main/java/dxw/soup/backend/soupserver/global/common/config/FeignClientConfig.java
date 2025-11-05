package dxw.soup.backend.soupserver.global.common.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "dxw.soup.backend.soupserver.domain")
public class FeignClientConfig {
}
