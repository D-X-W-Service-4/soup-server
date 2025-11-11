package dxw.soup.backend.soupserver.domain.file.facade;

import dxw.soup.backend.soupserver.domain.file.dto.response.FilePresignedUrlResponse;
import dxw.soup.backend.soupserver.global.common.external.s3.S3Service;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileFacade {

    private final S3Service s3Service;

    public FilePresignedUrlResponse generatePresignedUrl(String fileName, String contentType) {
        URL generatedPresignedUrl = s3Service.generatePresignedUploadUrl(fileName, contentType, 15);

        return new FilePresignedUrlResponse(generatedPresignedUrl.toString());
    }
}
