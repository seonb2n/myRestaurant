package com.example.myrestaurant.common.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.infrastructure.user.UserServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {

    private UserServiceImpl userService;

    public JWTCheckFilter(AuthenticationManager authenticationManager, UserServiceImpl userService) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearer == null || !bearer.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = bearer.substring("Bearer ".length());
        VerifyResult verifyResult = JWTUtil.verify(token);
        if(verifyResult.isSuccess()) {
            User user = (User) userService.loadUserByUsername(verifyResult.getEmail());
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(userToken);
            chain.doFilter(request, response);
    } else {
            throw new TokenExpiredException("Token is not valid");
        }
    }
}
