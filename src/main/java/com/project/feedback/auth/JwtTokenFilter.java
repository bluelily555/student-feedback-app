package com.project.feedback.auth;

import com.project.feedback.domain.entity.TokenEntity;
import com.project.feedback.domain.entity.UserEntity;
import com.project.feedback.exception.ErrorCode;
import com.project.feedback.repository.TokenRepository;
import com.project.feedback.service.FindService;
import com.project.feedback.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
public class JwtTokenFilter extends OncePerRequestFilter {

    private final FindService findService;
    private final String secretKey;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("RequestURL:{}", request.getRequestURL());
            // Token 꺼내기
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // authorizationHeader에 "Bearer + JwtToken"이 제대로 들어왔는지 체크
        if (authorizationHeader == null) {

            // 화면 로그인을 위해 Session에서 Token을 꺼내보는 작업 => 여기에도 없으면 인증 실패
            // 여기에 있으면 이 Token으로 인증 진행
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("jwt") == null) {
                // session null 이면 -> 인증이 안됨 -> 로그인 화면으로
                RequestDispatcher rd = request.getRequestDispatcher("/users/login");
                filterChain.doFilter(request, response);
                return;
            } else {
                authorizationHeader = request.getSession().getAttribute("jwt").toString();
            }
        }

        if (!authorizationHeader.startsWith("Bearer ")) {
            log.info("security:{}", "토큰이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.split(" ")[1];


        // 토큰이 만료되었는지 check
        // 토큰이 만료되면, access token이 만료된거니까, refresh token이 만료되었는지 확인해서 refresh token 만료안되면 access token 새로 발급해서
        // 다시 접속
        String tokenValidCheck = JwtTokenUtil.isValid(token, secretKey);
        HttpSession session = request.getSession(false);
        if(tokenValidCheck.equals(ErrorCode.EXPIRE_TOKEN.name())){
            // token expired 됐을때
            log.info(tokenValidCheck);
            log.info("이번 요청으로 사용될 token"+token);
            boolean isRefresh = userService.refreshToken(token, response, request);
            // token 재발급할지 말지 결정
            log.info("refresh: " + isRefresh);
            if(isRefresh){
                log.info("token 재발급 했음");
                filterChain.doFilter(request, response);
            }else{
                log.error("refresh token 만료됨");
                session.setAttribute("jwt", null);
                response.sendRedirect("/users/login");
            }
                return;
        }else if(tokenValidCheck.equals(ErrorCode.INVALID_TOKEN.name())){
            // invalid token
            log.error("invalid token");
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN);
//            filterChain.doFilter(request, response);
            return;
        }

        // Token에서 userName 꺼내기
        String userName = JwtTokenUtil.getUserName(token, secretKey);
        // userName으로 User 찾아오기
        UserEntity loginUser = findService.findUserByUserName(userName);

        log.info("role:{}", loginUser.getRole().name());

        // 권한을 주거나 안주기
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getUserName(), null, List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}