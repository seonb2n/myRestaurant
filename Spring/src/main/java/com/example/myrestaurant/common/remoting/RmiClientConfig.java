package com.example.myrestaurant.common.remoting;

import com.example.myrestaurant.domain.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class RmiClientConfig {

    @Bean
    public UserService userService() {
        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceInterface(UserService.class);
        factoryBean.setServiceUrl(
                "http://localhost:8833/invoker/httInvoker/userService"
        );
        factoryBean.afterPropertiesSet();
        return (UserService) factoryBean.getObject();
    }
}
