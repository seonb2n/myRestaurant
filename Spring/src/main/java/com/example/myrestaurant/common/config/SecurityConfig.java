package com.example.myrestaurant.common.config;

import com.example.myrestaurant.common.jwt.JWTCheckFilter;
import com.example.myrestaurant.common.jwt.JWTLoginFilter;
import com.example.myrestaurant.infrastructure.user.UserServiceImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;

    public SecurityConfig(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(), userService);
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), userService);


        http
                .csrf().disable()
                .httpBasic().and()
                .authorizeRequests(request -> {
                    request.antMatchers("/", "/userEnroll", "/getUserData", "/login").permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .rememberMe();
        ;

    }
}
