package me.yeeunhong.waitingforbooking;

import lombok.extern.slf4j.Slf4j;
import me.yeeunhong.waitingforbooking.dto.SignInDto;
import me.yeeunhong.waitingforbooking.dto.SignUpDto;
import me.yeeunhong.waitingforbooking.dto.UserDto;
import me.yeeunhong.waitingforbooking.jwt.JwtToken;
import me.yeeunhong.waitingforbooking.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    int randomServerPort;

    private SignUpDto signUpDto;

    @BeforeEach
    void beforeEach() {
        // User 회원가입
        signUpDto = SignUpDto.builder()
                .username("emilyyeeun@outlook.com")
                .password("12345678")
                .build();
    }


    @Transactional
    @Test
    public void signUpTest() {
        // API 요청 설정
        String url = "http://localhost:" + randomServerPort + "/users/sign-up";
        ResponseEntity<UserDto> responseEntity = testRestTemplate.postForEntity(url, signUpDto, UserDto.class);

        // 응답 검증
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDto savedMemberDto = responseEntity.getBody();
        assertThat(savedMemberDto.getUsername()).isEqualTo(signUpDto.getUsername());
    }


}
