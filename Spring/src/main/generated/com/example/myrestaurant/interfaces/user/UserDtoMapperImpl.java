package com.example.myrestaurant.interfaces.user;

import com.example.myrestaurant.domain.user.domain.UserCommand.UserLoginCommand;
import com.example.myrestaurant.domain.user.domain.UserCommand.UserLoginCommand.UserLoginCommandBuilder;
import com.example.myrestaurant.domain.user.domain.UserCommand.UserRegisterCommand;
import com.example.myrestaurant.domain.user.domain.UserCommand.UserRegisterCommand.UserRegisterCommandBuilder;
import com.example.myrestaurant.interfaces.user.UserDto.LoginUserRequest;
import com.example.myrestaurant.interfaces.user.UserDto.RegisterUserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-22T10:52:11+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.2 (Oracle Corporation)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public UserRegisterCommand of(RegisterUserRequest registerUserRequest) {
        if ( registerUserRequest == null ) {
            return null;
        }

        UserRegisterCommandBuilder userRegisterCommand = UserRegisterCommand.builder();

        userRegisterCommand.email( registerUserRequest.getEmail() );
        userRegisterCommand.password( registerUserRequest.getPassword() );
        userRegisterCommand.nickName( registerUserRequest.getNickName() );

        return userRegisterCommand.build();
    }

    @Override
    public UserLoginCommand of(LoginUserRequest loginUserRequest) {
        if ( loginUserRequest == null ) {
            return null;
        }

        UserLoginCommandBuilder userLoginCommand = UserLoginCommand.builder();

        userLoginCommand.email( loginUserRequest.getEmail() );
        userLoginCommand.password( loginUserRequest.getPassword() );

        return userLoginCommand.build();
    }
}
