package com.project.feedback;

import com.project.feedback.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import java.util.TimeZone;

@SpringBootApplication
public class FeedbackApplication {


    private final UserService userService;

    public FeedbackApplication(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    void setTimeZone() {
        userService.setDefaultUsers();
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

    }


    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> {
            p.setOneIndexedParameters(true);	// 1페이지 부터 시작
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(FeedbackApplication.class, args);
    }

}
