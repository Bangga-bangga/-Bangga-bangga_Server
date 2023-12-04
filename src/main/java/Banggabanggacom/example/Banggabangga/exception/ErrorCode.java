package Banggabanggacom.example.Banggabangga.exception;

public enum ErrorCode {

    DUPLICATED_EMAIL(4400, "이미 존재하는 E-mail입니다."),
    FAIL_VERIFY(4401, "인증 번호가 틀렸습니다."),
    NOT_SEND_MESSAGE(4402, "인증 번호가 전송되지 않았습니다."),
    DUPLICATED_NICKNAME(4403, "이미 존재하는 NickName입니다."),
    INVALID_MEMBERSHIP_FORM(4404, "유효하지 않는 회원가입 형태입니다."),

    HAS_NOT_AUTHORIZATION(4422, "권한이 없습니다."),
    POST_NOT_EXIST(4423,"해당 게시글을 찾을 수 없습니다."),
    COMMENT_NOT_EXIST(4424,"해당 댓글을 찾을 수 없습니다."),
    USER_NOT_EXIST(4433, "해당 유저를 찾을 수 없습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}