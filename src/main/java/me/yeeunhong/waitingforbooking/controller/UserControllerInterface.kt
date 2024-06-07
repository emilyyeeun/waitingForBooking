package me.yeeunhong.waitingforbooking.controller

import me.yeeunhong.waitingforbooking.dto.SignInDto
import me.yeeunhong.waitingforbooking.dto.SignUpDto
import me.yeeunhong.waitingforbooking.dto.UserDto
import me.yeeunhong.waitingforbooking.jwt.JwtToken
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

interface UserControllerInterface {
    fun signIn(@RequestBody signInDto: SignInDto?): JwtToken?
    fun signUp(@RequestBody signUpDto: SignUpDto?): ResponseEntity<UserDto?>?
}