package dxw.soup.backend.soupserver.domain.leveltest.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LevelTestErrorCode implements ErrorCode {
    LEVEL_TEST_NOT_FOUND("수준테스트 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LEVEL_TEST_ACCESS_DENIED("수준테스트에 접근이 불가능합니다.", HttpStatus.FORBIDDEN),
    ;

    private final String message;
    private final HttpStatus status;
}
