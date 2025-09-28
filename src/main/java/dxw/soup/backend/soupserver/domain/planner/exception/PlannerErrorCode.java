package dxw.soup.backend.soupserver.domain.planner.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PlannerErrorCode implements ErrorCode {
    PLANNER_NOT_FOUND("플래너를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PLANNER_ITEM_NOT_FOUND("플래너 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    PLANNER_ACCESS_DENIED("플래너에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    DUPLICATE_PLANNER("이미 해당 날짜에 플래너가 존재합니다.", HttpStatus.CONFLICT);
    private final String message;
    private final HttpStatus status;
}