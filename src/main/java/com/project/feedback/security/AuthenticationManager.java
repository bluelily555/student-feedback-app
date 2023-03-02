package com.project.feedback.security;

import com.project.feedback.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class AuthenticationManager implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("인증오류");
        String exception = String.valueOf(request.getAttribute("exception"));
        if (exception.equals(ErrorCode.EXPIRE_TOKEN.name())) {
            if (request.getRequestURL().toString().contains("api")) {
                log.error(ErrorCode.EXPIRE_TOKEN.getMessage());
                makeErrorResponse(response, ErrorCode.EXPIRE_TOKEN);
            } else response.sendRedirect("/users/login");
        } else {
            if (request.getRequestURL().toString().contains("api")) {
                log.error(ErrorCode.INVALID_TOKEN.getMessage());
                makeErrorResponse(response, ErrorCode.INVALID_TOKEN);
            } else response.sendRedirect("/users/login");
        }
//        makeErrorResponse(response, ErrorCode.INVALID_PERMISSION);
    }

    public void makeErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("text/html; charset=utf-8");
        String msg = "잘못된 접근입니다.";
        String url = "/users/login";
        PrintWriter w = response.getWriter();
        w.write("<script>alert('"+msg+"');location.href='"+url+"';</script>");
        w.flush();
        w.close();
    }
}
