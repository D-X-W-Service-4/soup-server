package dxw.soup.backend.soupserver.domain.questionset.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QuestionSetErrorCode implements ErrorCode {
    QUESTION_SET_NOT_FOUND("문제풀이 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    QUESTION_SET_ACCESS_DENIED("문제풀이에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ;

    private final String message;
    private final HttpStatus status;
}
