package com.project.feedback.auth;

import com.project.feedback.domain.entity.User;
import com.project.feedback.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
public class JwtTokenFilter extends OncePerRequestFilter {

    private final FindService findService;
    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // Token 꺼내기
            String authroizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // authorizationHeader에 "Bearer + JwtToken"이 제대로 들어왔는지 체크
            if(authroizationHeader == null) {

                // 화면 로그인을 위해 Session에서 Token을 꺼내보는 작업 => 여기에도 없으면 인증 실패
                // 여기에 있으면 이 Token으로 인증 진행
                HttpSession session = request.getSession(false);
                if(session == null || session.getAttribute("jwt") == null) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    authroizationHeader = request.getSession().getAttribute("jwt").toString();
                }
            }

            if (!authroizationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authroizationHeader.split(" ")[1];

            // 토큰이 만료되었는지 check
            if(JwtTokenUtil.isExpired(token, secretKey)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Token에서 userName 꺼내기
            String userName = JwtTokenUtil.getUserName(token, secretKey);

            // userName으로 User 찾아오기
            User loginUser = findService.findUserByUserName(userName);

            // 권한을 주거나 안주기
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginUser.getUserName(), null, List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 권한 부여
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
    }
}