package dxw.soup.backend.soupserver.domain.file.controller;

import dxw.soup.backend.soupserver.domain.file.dto.response.FilePresignedUrlResponse;
import dxw.soup.backend.soupserver.domain.file.facade.FileFacade;
import dxw.soup.backend.soupserver.global.common.annotation.RestApiController;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiController("/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileFacade fileFacade;

    @GetMapping("/presigned-url")
    public CommonResponse<FilePresignedUrlResponse> generatePresignedUrl(
            @RequestParam String fileName,
            @RequestParam String contentType
    ) {
        return CommonResponse.ok(fileFacade.generatePresignedUrl(
                URLDecoder.decode(fileName, StandardCharsets.UTF_8),
                URLDecoder.decode(contentType, StandardCharsets.UTF_8)
        ));
    }
}
