package dxw.soup.backend.soupserver.domain.model.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ModelErrorCode implements ErrorCode {
    GENERATED_PLANNER_ITEM_IS_EMPTY("생성된 플래너 항목이 비어 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    GENERATED_LEVEL_TEST_QUESTION_IS_EMPTY("생성된 수준테스트 문제 목록이 비어 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus status;
}
