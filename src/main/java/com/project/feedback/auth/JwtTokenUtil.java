package com.project.feedback.auth;

import com.project.feedback.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtil {

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // JWT Token 발급
    public static String createToken(String userName, String key, long expireTimeMs) {
        // Claim = 일종의 Map, userName을 넣어 줌으로써 나중에 userName을 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    // Claims에서 userName 꺼내기
    public static String getUserName(String token, String secretKey) {
        return extractClaims(token, secretKey).get("userName").toString();
    }


    // Token 검사
    public static String isValid(String token, String secretKey) {
        try{
            extractClaims(token, secretKey);
            return "OK";
        }catch(ExpiredJwtException e){
            return ErrorCode.EXPIRE_TOKEN.name();
        }catch(JwtException | IllegalArgumentException e){
            return ErrorCode.INVALID_TOKEN.name();
        }
    }
    public static boolean isRefreshTokenExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
