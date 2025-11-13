package dxw.soup.backend.soupserver.domain.question.exception;

import dxw.soup.backend.soupserver.global.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QuestionErrorCode implements ErrorCode {
    QUESTION_NOT_FOUND("문제 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SUBJECT_UNIT_NOT_FOUND("단원 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus status;
}
