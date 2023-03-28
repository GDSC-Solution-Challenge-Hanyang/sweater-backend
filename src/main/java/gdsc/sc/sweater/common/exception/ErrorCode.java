package gdsc.sc.sweater.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {


    MEMBER_NOT_FOUND(404, "1001", "없는 유저입니다."),
    MEMBER_DUPLICATED(409, "1002", "중복된 유저입니다."),

    EMPTY_POST_LIST(404, "2001", "게시물이 없어요.");

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}