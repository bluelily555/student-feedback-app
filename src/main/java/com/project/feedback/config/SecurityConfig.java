package com.project.feedback.config;

import com.project.feedback.auth.ExceptionHandlerFilter;
import com.project.feedback.auth.JwtTokenFilter;
import com.project.feedback.domain.Role;
import com.project.feedback.security.AuthenticationManager;
import com.project.feedback.security.CustomAccessDeniedHandler;
import com.project.feedback.application.FindService;
import com.project.feedback.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final FindService findService;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final CustomAccessDeniedHandler accessDeniedHandler;


    @Value("${jwt.token.secret}")
    private String secretkey;
    private static final String[] GET_ADMIN_MENTOR_MANAGE_USER = {
            "/admin/**",
            "/users",
            "/tasks/write",
            "/courses/write"
    };
    private static final String[] POST_ADMIN_MENTOR_MANAGE_USER = {
            "/api/v1/tasks",
            "/api/v1/users/**/role/change",
            "/crawler"
    };
    private static final String[] DELETE_ADMIN_MENTOR_MANAGE_USER = {
            "/api/v1/tasks"
    };
    private static final String[] PUT_ADMIN_MENTOR_MANAGE_USER = {
            "/api/v1/tasks"
    };
    private static final String[] GET_STUDENT_USER = {
            "/boards",
            "/course/students",
            "/users/my",
    };
    private static final String[] GET_STUDENT_ADMIN_MENTOR_MANAGE_USER = {
            "/courses/students",
            "/courses/**/students/weeks/**/days/**",
    };
    private static final String[] PERMIT_ALL = {
            "/",
            "/css/**", "/img/**",
            "/api/*/users/join",
            "/api/*/users/login",
            "/users/join",
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()
                .csrf().disable()
                .cors().and();

        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers(PERMIT_ALL).permitAll()
                .requestMatchers(HttpMethod.GET, GET_ADMIN_MENTOR_MANAGE_USER).hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name(), Role.ROLE_MENTOR.name())
                .requestMatchers(HttpMethod.POST, POST_ADMIN_MENTOR_MANAGE_USER).hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name(), Role.ROLE_MENTOR.name())
                .requestMatchers(HttpMethod.GET, GET_STUDENT_USER).hasAnyAuthority(Role.ROLE_STUDENT.name(), Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name(), Role.ROLE_MENTOR.name())
                .requestMatchers(HttpMethod.DELETE, DELETE_ADMIN_MENTOR_MANAGE_USER).hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name(), Role.ROLE_MENTOR.name())
                .requestMatchers(HttpMethod.PUT, PUT_ADMIN_MENTOR_MANAGE_USER).hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_MANAGER.name(), Role.ROLE_MENTOR.name())
                .requestMatchers(HttpMethod.GET, GET_STUDENT_ADMIN_MENTOR_MANAGE_USER).hasAnyAuthority(Role.ROLE_STUDENT.name(),Role.ROLE_ADMIN.name(), Role.ROLE_MENTOR.name(), Role.ROLE_MANAGER.name())
                .anyRequest().permitAll()
        );

// Session을 사용하는데 STATELESS를 쓴 것이 문제로 보여서 주석처리함
//        httpSecurity.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(authenticationManager)
                .accessDeniedHandler(accessDeniedHandler);

        httpSecurity.addFilterBefore(new JwtTokenFilter(findService, secretkey, userService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(), JwtTokenFilter.class);
        return httpSecurity.getOrBuild();
    }
}
