package com.example.myrestaurant.infrastructures.user;

import com.example.myrestaurant.common.exception.EmailNotFoundException;
import com.example.myrestaurant.common.exception.LoginWrongException;
import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.domain.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserReaderImpl implements UserReader {

    private final UserRepository userRepository;

    @Override
    public User getUserWithUserEmail(String userEmail) {
        var user = userRepository.findUserByEmail(userEmail);
        return user.orElseThrow(EmailNotFoundException::new);
    }

    @Override
    public User logInWithUserEmailAndPassword(String userEmail, String userPassword) {
        var user = userRepository.findUserByEmailAndPassword(userEmail, userPassword);
        return user.orElseThrow(LoginWrongException::new);
    }
}
