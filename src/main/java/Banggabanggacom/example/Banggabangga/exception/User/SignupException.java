package Banggabanggacom.example.Banggabangga.exception.User;

import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import lombok.Getter;

@Getter
public class SignupException extends RuntimeException{
    private final ErrorCode errorCode;

    public SignupException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
