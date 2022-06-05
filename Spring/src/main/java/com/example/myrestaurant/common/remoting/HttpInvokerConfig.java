package com.example.myrestaurant.common.remoting;

import com.example.myrestaurant.domain.user.domain.User;
import com.example.myrestaurant.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@Configuration
@RequiredArgsConstructor
public class HttpInvokerConfig {

    private final UserService userService;

    @Bean(name = "/httpInvoker/userService")
    public HttpInvokerServiceExporter httpInvokerServiceExporter() {
        HttpInvokerServiceExporter invokerService = new HttpInvokerServiceExporter();
        invokerService.setService(userService);
        invokerService.setServiceInterface(UserService.class);
        return invokerService;
    }
}
