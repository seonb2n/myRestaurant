package com.example.myrestaurant.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.myrestaurant.dto.user.domain.User;
import com.example.myrestaurant.dto.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService;

    public JWTLoginFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
        setFilterProcessesUrl("/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginForm userLoginForm = objectMapper.readValue(request.getInputStream(), UserLoginForm.class);

        if(userLoginForm.getRefreshToken() == null) {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userLoginForm.getEmail(), userLoginForm.getPassword(), null
            );

            return getAuthenticationManager().authenticate(token);
        } else {
            VerifyResult verifyResult = JWTUtil.verify(userLoginForm.getRefreshToken());
            if(verifyResult.isSuccess()) {
                User user = (User) userService.loadUserByUsername(verifyResult.getEmail());
                return new UsernamePasswordAuthenticationToken(
                        user, user.getAuthorities()
                );
            } else {
                throw new TokenExpiredException("refresh Token expired");
            }
        }

    }
}
