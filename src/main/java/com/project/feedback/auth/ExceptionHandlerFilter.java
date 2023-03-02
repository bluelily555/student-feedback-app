package com.project.feedback.auth;

import com.project.feedback.exception.CustomException;
import com.project.feedback.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);
        }catch (CustomException e){
            log.error(e.getErrorCode().name());
        } catch(ExpiredJwtException e){
            log.error("토큰만료");
            makeErrorResponse(response);
        }catch(JwtException | IllegalArgumentException e){
            log.error("유효하지 않은 토큰");
        }catch(ArrayIndexOutOfBoundsException e){
            log.error("토큰을 추출할 수 없습니다.");
        }catch(NullPointerException e){
            log.error("NULL 에러");
        }
    }
    public void makeErrorResponse(HttpServletResponse response) throws IOException {
//        response.setStatus(errorCode.getStatus().value());
        response.setContentType("text/html; charset=utf-8");
        String msg = "잘못된 접근입니다.";
        String url = "/users/login";
        PrintWriter w = response.getWriter();
        w.write("<script>alert('" + msg + "');location.href='" + url + "';</script>");
        w.flush();
        w.close();
    }
}
