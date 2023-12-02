package Banggabanggacom.example.Banggabangga.exception.User;

import Banggabanggacom.example.Banggabangga.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class CustomMethodArgumentNotValidException extends MethodArgumentNotValidException {
    private final ErrorCode errorCode;

    public CustomMethodArgumentNotValidException(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
        errorCode =ErrorCode.INVALID_MEMBERSHIP_FORM;
    }
}