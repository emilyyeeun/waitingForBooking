package me.yeeunhong.waitingforbooking;

import me.yeeunhong.waitingforbooking.dto.SignInDto;
import me.yeeunhong.waitingforbooking.dto.SignUpDto;
import me.yeeunhong.waitingforbooking.dto.UserDto;
import me.yeeunhong.waitingforbooking.jwt.JwtToken;
import me.yeeunhong.waitingforbooking.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTestJava {

    @Autowired
    UserService userService;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int randomServerPort;

    private SignUpDto signUpDto;

    @BeforeEach
    void beforeEach() {
        signUpDto = SignUpDto.builder()
                .username("emily@gmail.com")
                .password("12345678")
                .build();
    }

    @Test
    @Transactional
    public void signUpTest() {

        // API 요청 설정
        String url = "http://localhost:" + randomServerPort + "/users/sign-up";
        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity(url, signUpDto, UserDto.class);

        // 응답 검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDto savedMemberDto = responseEntity.getBody();
        assertThat(savedMemberDto.getUsername()).isEqualTo(signUpDto.getUsername());
    }

    @Test
    @Transactional
    public void signInTest() {
        userService.signUp(signUpDto);

        SignInDto signInDto = SignInDto.builder()
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword()).build();

        // 로그인 요청
        JwtToken jwtToken = userService.signIn(signInDto.getUsername(), signUpDto.getPassword());

        // HttpHeaders 객체 생성 및 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken.getAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // API 요청 설정
        String url = "http://localhost:" + randomServerPort + "/users/test";
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, new HttpEntity<>(httpHeaders), String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(signInDto.getUsername());

//        assertThat(SecurityUtil.getCurrentUsername()).isEqualTo(signInDto.getUsername()); // -> 테스트 코드에서는 인증을 위한 절차를 거치지 X. SecurityContextHolder 에 인증 정보가 존재하지 않는다.


    }

}
