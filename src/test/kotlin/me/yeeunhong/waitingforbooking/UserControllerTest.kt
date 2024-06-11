package me.yeeunhong.waitingforbooking

import me.yeeunhong.waitingforbooking.dto.SignInDto
import me.yeeunhong.waitingforbooking.dto.SignUpDto
import me.yeeunhong.waitingforbooking.dto.UserDto
import me.yeeunhong.waitingforbooking.service.UserService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import javax.transaction.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private var randomServerPort = 0
    private lateinit var signUpDto: SignUpDto

    @BeforeEach
    fun beforeEach() {
        signUpDto = SignUpDto(
                "emilyyeeun@outlook.com",
                "12345678")
    }

    @Transactional
    @Test
    fun signUpTest() {
        // API 요청 설정
        val url = "http://localhost:$randomServerPort/users/sign-up"
        val responseEntity = testRestTemplate.postForEntity(url, signUpDto, UserDto::class.java)

        println("Response body: $responseEntity.body")
        // 응답 검증
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        val savedMemberDto = responseEntity.body
        Assertions.assertThat(savedMemberDto.username).isEqualTo(signUpDto.username)
    }

    @Transactional
    @Test
    fun signInTest() {
        userService.signUp(signUpDto)
        val signInDto = SignInDto(signUpDto.username, signUpDto.password)


        // 로그인 요청
        val jwtToken = userService!!.signIn(signInDto.username, signInDto.password)

        // HttpHeaders 객체 생성 및 토큰 추가
        val httpHeaders = HttpHeaders()
        httpHeaders.setBearerAuth(jwtToken?.accessToken)
        httpHeaders.contentType = MediaType.APPLICATION_JSON

        // API 요청 설정
        val url = "http://localhost:$randomServerPort/users/test"
        val responseEntity = testRestTemplate!!.postForEntity(url, HttpEntity<Any>(httpHeaders), String::class.java)
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(responseEntity.body).isEqualTo(signInDto.username)
    }
}