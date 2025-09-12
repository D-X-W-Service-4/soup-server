package dxw.soup.backend.soupserver.global.common.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import dxw.soup.backend.soupserver.global.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ApiResponse<?> handleApiException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        log.error("에러 발생: ({}) {}", errorCode.name(), errorCode.getMessage());

        return ApiResponse.error(errorCode);
    }
}
