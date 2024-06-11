package me.yeeunhong.waitingforbooking.config

import lombok.RequiredArgsConstructor
import me.yeeunhong.waitingforbooking.jwt.JwtAuthenticationFilter
import me.yeeunhong.waitingforbooking.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@RequiredArgsConstructor
@Configuration
open class WebSecurityConfig(private val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigInterface {

    @Bean
    override fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(AntPathRequestMatcher("/h2-console/**"))
        }
    }

    @Bean
    override fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
            .httpBasic().disable()
            .csrf().disable() // JWT를 사용하기 때문에 세션을 사용하지 않음
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests() // 해당 API에 대해서는 모든 요청을 허가
            .antMatchers("/users/sign-up").permitAll()
            .antMatchers("/users/sign-in").permitAll() // USER 권한이 있어야 요청할 수 있음
            .antMatchers("/users/test").hasRole("USER") // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
            .anyRequest().authenticated()
            .and() // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java
            ).build()
    }

    @Bean
    override fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

