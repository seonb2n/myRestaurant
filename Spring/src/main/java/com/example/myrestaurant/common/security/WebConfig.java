package com.example.myrestaurant.common.security;

import com.example.myrestaurant.common.monitoring.AppStatistics;
import com.example.myrestaurant.common.monitoring.AppStatisticsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.example.myrestaurant"})
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(false);
        registry.setOrder(1);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB_INF/jsp/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }

//Spring bean 을 JMX 로 노출시키기 위한 설정
//    @Bean
//    AppStatistics appStatistics() {
//        return new AppStatisticsImpl();
//    }
//
//    @Bean
//    MBeanExporter jmxExporter() {
//        MBeanExporter exporter = new MBeanExporter();
//        Map<String, Object> beans = new HashMap<>();
//        beans.put("bean:name=ProSpringApp", appStatistics());
//        exporter.setBeans(beans);
//        return exporter;
//    }

}
