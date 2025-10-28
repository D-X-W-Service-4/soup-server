package dxw.soup.backend.soupserver.global.common.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import dxw.soup.backend.soupserver.global.common.code.GlobalErrorCode;
import dxw.soup.backend.soupserver.global.common.dto.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public CommonResponse<?> handleApiException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        log.error("에러 발생: ({}) {}", errorCode.name(), errorCode.getMessage());

        return CommonResponse.error(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception exception, HttpServletRequest request) {

        ErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        HttpStatus httpStatus = errorCode.getStatus();

        exception.printStackTrace();

        return CommonResponse.error(exception, httpStatus);
    }
}
