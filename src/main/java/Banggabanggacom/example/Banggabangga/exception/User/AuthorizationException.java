package Banggabanggacom.example.Banggabangga.exception.User;

import Banggabanggacom.example.Banggabangga.exception.ErrorCode;

public class AuthorizationException extends RuntimeException {
    private final ErrorCode errorCode;

    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}