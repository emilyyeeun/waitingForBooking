package me.yeeunhong.waitingforbooking.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.time.Duration;
import java.util.Set;

/*
토큰을 생성하고 올바른 토큰인지 유효성 검사를 하고,
토큰에서 필요한 정보를 가져오는 클래스
 */
@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user) {
        Date now = new Date();
        return Jwts.builder()
                // 헤더의 타입 지정이 가능, jwt를 사용하기 때문에 Header.JWT_TYPE로 사용
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 등록된 클레임 중 토큰 발급자를 설정
                .setIssuer(jwtProperties.getIssuer())
                // 등록된 클레임 중, 발급 시간을 설정, Date 타입만 추가 가능
                .setIssuedAt(now)
                // 만료 시간을 설정 가능, Date 타입만 추가 가능
                .setExpiration(expiry)
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                // 비공개 클레임을 설정할 수 있다. (해싱 알고리즘과 시크릿 키 설정)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();

    }
}
