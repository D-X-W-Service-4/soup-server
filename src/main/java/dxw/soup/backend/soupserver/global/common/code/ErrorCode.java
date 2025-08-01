package dxw.soup.backend.soupserver.global.common.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String getMessage();
    String name();
}
