package me.yeeunhong.waitingforbooking.service

import lombok.RequiredArgsConstructor
import me.yeeunhong.waitingforbooking.domain.User
import me.yeeunhong.waitingforbooking.dto.SignUpDto
import me.yeeunhong.waitingforbooking.dto.UserDto
import me.yeeunhong.waitingforbooking.jwt.JwtToken
import me.yeeunhong.waitingforbooking.jwt.JwtTokenProvider
import me.yeeunhong.waitingforbooking.repository.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@RequiredArgsConstructor
@Transactional
open class UserService (
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserServiceInterface {

    @Transactional
    override fun signIn(username: String, password: String): JwtToken {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 User 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        val jwtToken = jwtTokenProvider.generateToken(authentication)
        SecurityContextHolder.getContext().authentication = authentication
        val headers = HttpHeaders()
        // response header에 jwt token을 넣어줌
        headers.add("Authorization", "Bearer $jwtToken")
        return jwtToken
    }

    @Transactional
    override fun signUp(signUpDto: SignUpDto): UserDto {
        require(!userRepository.existsByUsername(signUpDto.username)) { "이미 사용 중인 사용자 이름입니다." }

        // Password 암호화
        val encodedPassword = passwordEncoder.encode(signUpDto.password)
        val roles: MutableList<String> = ArrayList()
        roles.add("ROLE_USER");

//        val signUpUser : User = if (signUpDto.roles.contains("ROLE_ADMIN")) {
//            roles.add("ROLE_ADMIN")
//            signUpAdmin(signUpDto)
//        } else {
//            roles.add("ROLE_USER")
//            signUpUser(signUpDto)
//        }
//        userRepository.save(signUpUser)

        return UserDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)))
    }

//    private fun signUpUser(signUpDto: SignUpDto) : User {
//        return User.builder()
//            .username(signUpDto.username)
//            .password(signUpDto.password)
//            .roles(signUpDto.roles)
//            .build()
//    }
//
//    private fun signUpAdmin(signUpDto: SignUpDto) : User {
//        return User.builder()
//            .username(signUpDto.username)
//            .password(signUpDto.password)
//            .roles(signUpDto.roles)
//            .build()
//    }
}