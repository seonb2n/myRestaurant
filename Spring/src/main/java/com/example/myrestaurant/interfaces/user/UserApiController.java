package com.example.myrestaurant.interfaces.user;


import com.example.myrestaurant.application.UserFacade;
import com.example.myrestaurant.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserFacade userFacade;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/register")
    public CommonResponse registerUser(@RequestBody @Valid UserDto.RegisterUserRequest registerUserRequest) {
        var userCommand = userDtoMapper.of(registerUserRequest);
        var userInfo = userFacade.registerUser(userCommand);
        var response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response, "회원 가입이 완료됐습니다.");
    }

    @PostMapping("/login")
    public CommonResponse logInUser(@RequestBody @Valid UserDto.LoginUserRequest loginUserRequest) {
        var userCommand = userDtoMapper.of(loginUserRequest);
        var userInfo = userFacade.logInUser(userCommand);
        var response = new UserDto.LoginResponse(userInfo);
        return CommonResponse.success(response, "로그인 성공");
    }
}
