package com.example.myrestaurant.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.myrestaurant.dto.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("seonbin");
    private static final long AUTH_TIME = 60 * 20;
    private static final long REFRESH_TIME = 60 * 60 * 24 * 7;

    public static String makeAuthToken(User user) {
        return JWT.create().withSubject(user.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
        .sign(ALGORITHM);
    }

    public static String makeRefreshToken(User user) {
        return JWT.create().withSubject(user.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token) {
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder().success(true)
                    .email(verify.getSubject()).build();
        } catch (Exception e) {
            DecodedJWT decodedJWT = JWT.decode(token);
            return VerifyResult.builder().success(false)
                    .email(decodedJWT.getSubject()).build();
        }
    }

}
