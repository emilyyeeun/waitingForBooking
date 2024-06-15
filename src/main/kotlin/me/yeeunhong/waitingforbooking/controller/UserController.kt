package me.yeeunhong.waitingforbooking.controller

import lombok.RequiredArgsConstructor
import me.yeeunhong.waitingforbooking.dto.SignInDto
import me.yeeunhong.waitingforbooking.dto.SignUpDto
import me.yeeunhong.waitingforbooking.dto.UserDto
import me.yeeunhong.waitingforbooking.jwt.JwtToken
import me.yeeunhong.waitingforbooking.jwt.SecurityUtil
import me.yeeunhong.waitingforbooking.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
class UserController @Autowired constructor(private val userService: UserService): UserControllerInterface {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java);
    }
    @PostMapping("/sign-in")
    override fun signIn(@RequestBody signInDto: SignInDto): JwtToken {
        val jwtToken = userService.signIn(signInDto.username, signInDto.password)
        log.info("request username = ${signInDto.username}, password = ${signInDto.password}")
        log.info("jwtToken accessToken = $jwtToken!!.accessToken, refreshToken = $jwtToken!!.refreshToken")
        return jwtToken!!
    }

    @PostMapping("/test")
    fun test(): String {
        return SecurityUtil.currentUsername
    }

    @PostMapping("/sign-up")
    override fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<UserDto> {
        val savedUserDto = userService.signUp(signUpDto)
        log.info("username = ${signUpDto.username}, password = ${signUpDto.password}")
        return ResponseEntity.ok(savedUserDto)
    }
}