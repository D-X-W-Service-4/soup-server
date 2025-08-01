package dxw.soup.backend.soupserver.global.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import dxw.soup.backend.soupserver.global.common.code.GlobalErrorCode;
import dxw.soup.backend.soupserver.global.common.code.SuccessCode;
import org.springframework.http.HttpStatus;

public record ApiResponse<T>(
        int status,
        String code,
        String message,
        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        T data
) {
    public static <T> ApiResponse<T> success(final SuccessCode successCode) {
        return new ApiResponse<>(successCode.getStatus().value(), SuccessCode.CODE, SuccessCode.MESSAGE, null);
    }

    public static <T> ApiResponse<T> success(final SuccessCode successCode, final T data) {
        return new ApiResponse<>(successCode.getStatus().value(), SuccessCode.CODE, SuccessCode.MESSAGE, data);
    }

    public static ApiResponse<Void> error(final ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getStatus().value(), errorCode.name(), errorCode.getMessage(), null);
    }

    public static ApiResponse<Void> error(final Exception exception, HttpStatus status) {
        return new ApiResponse<>(status.value(), status.name(), exception.getMessage(), null);
    }

    public static ApiResponse<Void> error(final Exception exception) {
        GlobalErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        String message = exception.getClass().getSimpleName() + ": " + exception.getMessage();

        return new ApiResponse<>(errorCode.getStatus().value(), errorCode.name(), message, null);
    }
}
