package me.yeeunhong.waitingforbooking.controller

import me.yeeunhong.waitingforbooking.dto.SignInDto
import io.github.oshai.kotlinlogging.KotlinLogging
import me.yeeunhong.waitingforbooking.dto.SignUpDto
import me.yeeunhong.waitingforbooking.dto.UserDto
import me.yeeunhong.waitingforbooking.jwt.JwtToken
import me.yeeunhong.waitingforbooking.jwt.SecurityUtil
import me.yeeunhong.waitingforbooking.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
): UserControllerInterface {
    private val log = KotlinLogging.logger {}

    @PostMapping("/sign-in")
    override fun signIn(@RequestBody signInDto: SignInDto): JwtToken {
        val jwtToken = userService.signIn(signInDto.username, signInDto.password)
        log.info{"request username = ${signInDto.username}, password = ${signInDto.password}"}
        log.info{"jwtToken accessToken = $jwtToken.accessToken, refreshToken = $jwtToken.refreshToken"}
        return jwtToken!!
    }

    @PostMapping("/test")
    fun test(): String {
        return SecurityUtil.currentUsername
    }

    @PostMapping("/sign-up")
    override fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<UserDto> {
        val savedUserDto = userService.signUp(signUpDto)
        log.info { "username = ${signUpDto.username}, password = ${signUpDto.password}" }
        return ResponseEntity.ok(savedUserDto)
    }
}