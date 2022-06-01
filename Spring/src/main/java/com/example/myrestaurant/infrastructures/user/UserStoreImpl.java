package com.example.myrestaurant.infrastructures.user;

import com.example.myrestaurant.common.exception.AlreadyExistEmailException;
import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.domain.user.service.UserReader;
import com.example.myrestaurant.domain.user.service.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserStoreImpl implements UserStore {

    private final UserRepository userRepository;

    @Override
    public User store(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User initUser) {
        Optional<User> user = userRepository.findUserByEmail(initUser.getEmail());
        if(user.isPresent()) {
            throw new AlreadyExistEmailException();
        } else {
            return userRepository.save(initUser);
        }
    }
}
