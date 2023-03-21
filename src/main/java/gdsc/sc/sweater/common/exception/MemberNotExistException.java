package gdsc.sc.sweater.common.exception;

import org.springframework.http.HttpStatus;

public class MemberNotExistException extends ResponseStatusReasonException{
    public MemberNotExistException() {
        super(
                HttpStatus.FORBIDDEN,
                "MEMBER_NOT_FOUND",
                "등록되지 않은 사용자입니다."
        );
    }
}
