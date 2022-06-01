package com.example.myrestaurant.common.response;

import com.example.myrestaurant.common.exception.AlreadyExistEmailException;
import com.example.myrestaurant.common.exception.EmailNotFoundException;
import com.example.myrestaurant.common.exception.LoginWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 예외 처리를 하기 위한 advice
 * 1. 이미 존재하는 회원 아이디
 * 2. 아이디 비밀번호 오류
 */

@ControllerAdvice
public class CommonControllerAdvice {


    /**
     * http status: 500 result Fail
     * 내부 시스템 예외 상황
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception e) {
        return CommonResponse.fail(e.getMessage());
    }

    /**
     * http status: 200 And result Fail
     * 회원 가입 시 이미 존재하는 email 로 가입 시도
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = AlreadyExistEmailException.class)
    public CommonResponse alreadyExistEmailRegisterException(AlreadyExistEmailException e) {
        return CommonResponse.fail(e.getMessage());
    }

    /**
     * http status: 400 And result Fail
     * request paramet 에러 처리(dto 유효성 검사 결과 반환)
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        if(fe != null) {
            return CommonResponse.fail(fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")");
        }
        else {
            return CommonResponse.fail("입력 값이 잘못됐습니다.");
        }
    }

    /**
     * http status: 200 And result Fail
     * 로그인 시 아이디 및 비밀번호 오류
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {LoginWrongException.class, EmailNotFoundException.class})
    public CommonResponse logInErrorException(RuntimeException e) {
        return CommonResponse.fail(e.getMessage());
    }

}
