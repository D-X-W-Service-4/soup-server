package dxw.soup.backend.soupserver.global.common.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import dxw.soup.backend.soupserver.global.common.code.GlobalErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ApiException(Throwable cause) {
        this(GlobalErrorCode.INTERNAL_SERVER_ERROR, cause);
    }
}
