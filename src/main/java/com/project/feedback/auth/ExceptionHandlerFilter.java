package com.project.feedback.auth;

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
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException e){
//            log.error("토큰만료");
//        }catch(JwtException | IllegalArgumentException e){
//            log.error("유효하지 않은 토큰");
        }catch(ArrayIndexOutOfBoundsException e){
            log.error("토큰을 추출할 수 없습니다.");
        }catch(NullPointerException e){
            log.error("NULL 에러");
            RequestDispatcher rd = request.getRequestDispatcher("/users/login");
            filterChain.doFilter(request, response);
        }
    }


}
