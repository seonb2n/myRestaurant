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

    @Bean
    public InternalResourceViewResolver resolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    public void configureDefaultServerletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
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
