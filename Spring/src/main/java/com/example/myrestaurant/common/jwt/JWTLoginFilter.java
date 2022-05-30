package com.example.myrestaurant.common.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.myrestaurant.common.config.UserLoginForm;
import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.infrastructure.user.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    private UserServiceImpl userService;

    public JWTLoginFilter(AuthenticationManager authenticationManager, UserServiceImpl userService) {
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

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        response.setHeader("auth_token", JWTUtil.makeAuthToken(user));
        response.setHeader("refresh_token", JWTUtil.makeRefreshToken(user));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        //return 값을 JSON 형태로 설정
        response.getOutputStream().write(objectMapper.writeValueAsBytes(user));
    }

}
