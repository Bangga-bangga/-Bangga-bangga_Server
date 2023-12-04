package Banggabanggacom.example.Banggabangga.exception;

import Banggabanggacom.example.Banggabangga.exception.User.AuthorizationException;
import Banggabanggacom.example.Banggabangga.exception.User.CustomMethodArgumentNotValidException;
import Banggabanggacom.example.Banggabangga.exception.User.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@RequiredArgsConstructor
public class customizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // 모든 예외를 처리하는 메소드
    // Bean 내에서 발생하는 예외를 처리
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // 사용자가 존재하지 않았을 때 처리하는 메소드
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(ErrorCode.USER_NOT_EXIST.getCode())
                .message(ErrorCode.USER_NOT_EXIST.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(ErrorCode.HAS_NOT_AUTHORIZATION.getCode())
                .message(ErrorCode.HAS_NOT_AUTHORIZATION.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.FORBIDDEN);

    }

//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
//
//        BindingResult bindingResult = ex.getBindingResult();
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        List<String> errorMessages = fieldErrors.stream()
//                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        return ExceptionResponse.builder()
//                .code(ErrorCode.INVALID_MEMBERSHIP_FORM.getCode())
//                .message(String.join(", ", errorMessages))
//                .details(request.getDescription(false))
//                .build();
//    }


}