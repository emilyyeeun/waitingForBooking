package me.yeeunhong.waitingforbooking.service;

//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import me.yeeunhong.waitingforbooking.dto.SignUpDto;
//import me.yeeunhong.waitingforbooking.dto.UserDto;
//import me.yeeunhong.waitingforbooking.jwt.JwtToken;
//import me.yeeunhong.waitingforbooking.jwt.JwtTokenProvider;
//import me.yeeunhong.waitingforbooking.repository.UserRepository;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;

//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class UserService implements UserServiceInterface{
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Transactional
//    public JwtToken signIn(String username, String password) {
//        // 1. username + password 를 기반으로 Authentication 객체 생성
//        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 User 에 대한 검증 진행
//        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        HttpHeaders headers = new HttpHeaders();
//        // response header에 jwt token을 넣어줌
//        headers.add("Authorization", "Bearer " + jwtToken);
//        return jwtToken;
//    }
//
//    @Transactional
//    public UserDto signUp(SignUpDto signUpDto) {
//        if (userRepository.existsByUsername(signUpDto.getUsername())) {
//            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
//        }
//        // Password 암호화
//        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
//        List<String> roles = new ArrayList<>();
//        roles.add("ROLE_USER");  // USER 권한 부여
//        return UserDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
//    }
//}