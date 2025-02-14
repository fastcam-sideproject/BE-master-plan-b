package com.example.masterplanbbe.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum ErrorCode {
    // Common
    BAD_REQUEST(400, "C001", "요청 파라미터 혹은 요청 바디의 값을 다시 확인하세요."),
    INTERNAL_SERVER_ERROR(500, "C002", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C003", "유효하지 않은 입력입니다."),
    DATETIME_INVALID(400, "C005", "유효하지 않은 날짜입니다"),
    MESSAGE_SEND_FAIL(400, "C006", "메시지 전송 실패"),

    // User
    USER_NOT_FOUND(404, "U001", "사용자를 찾을 수 없습니다."),
    SOME_USERS_NOT_FOUND(404, "U002", "일부 사용자를 찾을 수 없습니다."),
    USER_ID_NOT_INITIALIZED(400, "U003", "사용자 ID가 초기화되지 않았습니다."),
    LOGIN_FAIL(401, "U004", "아이디 또는 비밀번호가 잘못되었습니다. 다시 시도해주세요."),

    // Auth
    DUPLICATED_PHONE_NUMBER(409, "A001", "이미 등록된 전화번호입니다."),
    UNAUTHORIZED_ACCESS(HttpServletResponse.SC_UNAUTHORIZED, "A002", "로그인 후 접속해주세요."),
    FORBIDDEN_ACCESS(HttpServletResponse.SC_FORBIDDEN, "A003", "접근 권한이 부족합니다"),
    LOGOUT_EXCEPTION(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "A004", "비정상적인 로그아웃! 관리자에게 문의하세요"),

    // Oauth
    SOCIAL_EMAIL_LOAD_FAIL(400, "O001", "소셜 로그인에서 이메일을 불러올 수 없습니다."),
    SOCIAL_NAME_LOAD_FAIL(400, "O002", "소셜 로그인에서 이름을 불러올 수 없습니다."),

    // Exam
    EXAM_NOT_FOUND(404, "E001", "시험을 찾을 수 없습니다."),

    // Post
    INVALID_INPUT_TITLE(400,"P001","유효하지 않은 타이틀입니다."),
    INVALID_INPUT_CONTENT(400,"P002","유효하지 않은 내용입니다."),
    NOT_FIND_POST(400,"P003" ,"게시글을 찾을 수 없습니다." ),
    NOT_DELETED_POST(400,"P004","게시글 삭제 권한이 없습니다."),
    NOT_MODIFIED_POST(400,"P005" ,"게시글 수정 권한이 없습니다." ),

    // Comment
    NOT_FOUND_COMMENT(400, "C001" ,"댓글을 찾을 수 없습니다." ),
    NOT_BELONG_COMMENT(400,"C002" ,"속해있는 댓글이 아닙니다." ),
    NOT_MODIFIED_COMMENT(400,"C003" , "댓글 수정 권한이 없습니다."),

    // UserExamSession
    NOT_FOUND_USER_EXAM_SESSION(400, "UES001", "해당하는 시험 일정을 찾을 수 없습니다.")
    ;

    private final Integer status;
    private final String code;
    private final String message;
}
