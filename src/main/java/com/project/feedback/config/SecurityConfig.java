package com.project.feedback.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.feedback.auth.JwtTokenFilter;
import com.project.feedback.domain.Response;
import com.project.feedback.domain.Role;
import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.service.FindService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                .authorizeRequests()
                //user 목록 admin만 가져올 수 있음
                .antMatchers(HttpMethod.GET, "/users").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/v1/tasks").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/v1/tasks").hasAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/v1/tasks").hasAuthority(Role.ADMIN.name())


            .antMatchers(HttpMethod.POST, "/api/v1/users/**/role/change").hasAuthority(Role.ADMIN.name())
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // UserNamePasswordAuthenticationFilter을 적용 하기 전에 JwtTokenFilter를 적용
                .addFilterBefore(new JwtTokenFilter(findService, secretkey), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // 인증 실패 시 INVALID_PERMISSION 에러 발생
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                        makeErrorResponse(response, ErrorCode.INVALID_PERMISSION);
                    }
                })
                // 인가 실패 시 INVALID_PERMISSION 에러 발생
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                        makeErrorResponse(response, ErrorCode.INVALID_PERMISSION);
                    }
                })
                .and()
                .build();
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
