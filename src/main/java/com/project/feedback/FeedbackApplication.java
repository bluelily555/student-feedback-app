package com.project.feedback;

import com.project.feedback.service.CourseService;
import com.project.feedback.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import java.util.TimeZone;

@SpringBootApplication
@AllArgsConstructor
public class FeedbackApplication {


    private final UserService userService;
    private final CourseService courseService;


    @PostConstruct
    void setTimeZone() {
        userService.setDefaultUsers();
        courseService.setDefaultCourse();
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
