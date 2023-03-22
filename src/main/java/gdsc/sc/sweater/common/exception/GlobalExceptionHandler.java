package gdsc.sc.sweater.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ResponseEntity 형태로 전역적으로 exception 을 리턴하는 클래스
 * throw CustomException(ENUM_TYPE) 시 @ExceptionHandler 에서 받아서 처리
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, resHeaders, HttpStatus.resolve(errorCode.getStatus()));
    }
}