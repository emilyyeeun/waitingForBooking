package me.yeeunhong.waitingforbooking.jwt

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Component
class JwtTokenProvider(@Value("\${jwt.secret_key}") secretKey: String) :
    JwtTokenProviderInterface {
    private val key: Key

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    // application.yml에서 secret 값 가져와서 key에 저장
    init {
        val keyBytes = Base64.decodeBase64(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    override fun generateToken(authentication: Authentication): JwtToken {
        // 권한 가져오기
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        val now = Date().time

        // Access Token 생성
        val accessTokenExpiresIn = Date(now + 86400000)
        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        // Refresh Token 생성
        val refreshToken = Jwts.builder()
            .setExpiration(Date(now + 86400000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return JwtToken(
            grantType = "Bearer",
            accessToken = accessToken,
            refreshToken = refreshToken)
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    override fun getAuthentication(accessToken: String): Authentication {
        // Jwt 토큰 복호화
        val claims = parseClaims(accessToken)
        if (claims["auth"] == null) {
            throw RuntimeException("권한 정보가 없는 토큰입니다.")
        }

        // 클레임에서 권한 정보 가져오기
        val authorities: Collection<GrantedAuthority> = Arrays.stream(
            claims["auth"].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
            .map { role: String? ->
                SimpleGrantedAuthority(
                    role
                )
            }
            .collect(Collectors.toList())
        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
        val principal: UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    // 토큰 정보를 검증하는 메서드
    override fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger.info(e) { "${"Invalid JWT Token"}" }
        } catch (e: MalformedJwtException) {
            logger.info(e) { "${"Invalid JWT Token"}" }
        } catch (e: ExpiredJwtException) {
            logger.info(e) { "${"Expired JWT Token"}" }
        } catch (e: UnsupportedJwtException) {
            logger.info(e) { "${"Unsupported JWT Token"}" }
        } catch (e: IllegalArgumentException) {
            logger.info(e) { "${"JWT claims string is empty."}" }
        }
        return false
    }

    // accessToken
    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
