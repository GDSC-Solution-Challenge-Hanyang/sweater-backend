package gdsc.sc.sweater.common.exception2;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    /**
     * 1. auth
     */
    USER_NOT_FOUND(401, "1001", "유저를 찾지 못했습니다"),
    NO_EMAIL_ERROR(400, "1002", "이메일을 입력해주세요"),
    NO_PWD_ERROR(400, "1003", "비밀번호를 입력해주세요"),
    NAME_EMPTY_ERROR(400, "1004", "이름을 입력해주세요."),
    EMAIL_FORMAT_ERROR(400, "1005", " 이메일 형식을 확인해주세요."),


    /**
     * 2. course
     */
    COURSE_NOT_FOUND(406, "2001", "강의/챕터를 찾을 수 없습니다."),

    /**
     * 3. post
     */
    EMPTY_BEST_POSTS(400, "3000", "인기 게시물이 없어요."), //3.1 인기게시물
    NO_TITLE_ERROR(400, "3001", "게시물 제목을 입력해주세요."),
    NO_CONTENT_ERROR(400, "3002", "게시물 본문을 입력해주세요."),
    INVALID_POST_CATEGORY(400, "3003", "유효하지 않은 카테고리 입니다."), //3.3 게시물 작성

    EMPTY_POST_LIST(406, "3004", "게시물이 없어요"), ///3.2 게시물리스트
    POST_NOT_EXIST(400, "3005", "게시물을 찾지 못했습니다."),  //3.4 게시물 상세
    DELETED_POST(406, "3006", "삭제된 게시물입니다."),
    POST_MODIFY_FAIL(403, "3007", "게시물 수정에 실패했습니다."),

    /**
     * 4. comment
     */
    FAILED_TO_CREATECOMMENT(406, "4001", "댓글 생성에 실패했습니다"), //4.1
    COMMENT_NOT_EXIST(406, "4002", "댓글을 찾지 못했습니다."),
    COMMENT_MODIFY_FAIL(403, "4003", "댓글 수정/삭제에 실패했습니다."),

    /**
     * 5. server
     */
    DATABASE_ERROR (500, "5001", "데이터베이스 에러"),
    INTERNAL_SERVER_ERROR(500, "5002", "서버 에러");

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