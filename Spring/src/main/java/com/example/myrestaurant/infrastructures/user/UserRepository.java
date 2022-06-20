package com.example.myrestaurant.infrastructures.user;

import com.example.myrestaurant.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String userEmail);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserByUserToken(String userToken);

    Optional<User> findUserByNickName(String nickName);
}
