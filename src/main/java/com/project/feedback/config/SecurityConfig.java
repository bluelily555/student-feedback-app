package com.project.feedback.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.feedback.auth.JwtTokenFilter;
import com.project.feedback.domain.Response;
import com.project.feedback.domain.Role;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.FindService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final FindService findService;

    @Value("${jwt.token.secret}")
    private String secretkey;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority(Role.ROLE_ADMIN.name())
                        //task 등록 admin만 가능
                        .requestMatchers(HttpMethod.GET, "/tasks/write").hasAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks").hasAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks").hasAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tasks").hasAuthority(Role.ROLE_ADMIN.name())
                        //코스 생성 관련 권한 ADMIN, MANAGER
                        .requestMatchers(HttpMethod.GET, "/courses/write").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks").hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**/role/change").hasAuthority(Role.ROLE_ADMIN.name())
                        .anyRequest().permitAll())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        makeErrorResponse(response, ErrorCode.INVALID_PERMISSION);
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

                        makeErrorResponse(response, ErrorCode.INVALID_PERMISSION);
                    }
                })
                .and()
                .addFilterBefore(new JwtTokenFilter(findService, secretkey), UsernamePasswordAuthenticationFilter.class)
                .getOrBuild();
    }


    // Security Filter Chain에서 발생하는 Exception은 ExceptionManager 까지 가지 않기 때문에 여기서 직접 처리
    public void makeErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), Response.error(new CustomException(errorCode)));
    }

}
