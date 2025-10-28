package dxw.soup.backend.soupserver.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import dxw.soup.backend.soupserver.global.common.code.GlobalErrorCode;
import dxw.soup.backend.soupserver.global.common.code.SuccessCode;
import org.springframework.http.HttpStatus;

public record CommonResponse<T>(
        int status,
        String code,
        String message,
        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        T data
) {
    public static CommonResponse<?> ok() {
        return success(SuccessCode.OK);
    }

    public static <T> CommonResponse<T> ok(final T data) {
        return success(SuccessCode.OK, data);
    }

    public static <T> CommonResponse<T> success(final SuccessCode successCode) {
        return new CommonResponse<>(successCode.getStatus().value(), SuccessCode.CODE, SuccessCode.MESSAGE, null);
    }

    public static <T> CommonResponse<T> success(final SuccessCode successCode, final T data) {
        return new CommonResponse<>(successCode.getStatus().value(), SuccessCode.CODE, SuccessCode.MESSAGE, data);
    }

    public static CommonResponse<Void> error(final ErrorCode errorCode) {
        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.name(), errorCode.getMessage(), null);
    }

    public static CommonResponse<Void> error(final Exception exception, HttpStatus status) {
        return new CommonResponse<>(status.value(), status.name(), exception.getMessage(), null);
    }

    public static CommonResponse<Void> error(final Exception exception) {
        GlobalErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        String message = exception.getClass().getSimpleName() + ": " + exception.getMessage();

        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.name(), message, null);
    }
}
