package com.example.myrestaurant.interfaces.user;

import com.example.myrestaurant.domain.user.domain.UserCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserDtoMapper {

    UserCommand.UserRegisterCommand of(UserDto.RegisterUserRequest registerUserRequest);

    UserCommand.UserLoginCommand of(UserDto.LoginUserRequest loginUserRequest);
}
