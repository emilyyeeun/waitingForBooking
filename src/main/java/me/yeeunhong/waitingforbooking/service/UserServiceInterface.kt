package me.yeeunhong.waitingforbooking.service

import me.yeeunhong.waitingforbooking.dto.SignUpDto
import me.yeeunhong.waitingforbooking.dto.UserDto
import me.yeeunhong.waitingforbooking.jwt.JwtToken

import javax.transaction.Transactional

interface UserServiceInterface {
    @Transactional
    fun signIn(username: String, password: String): JwtToken

    @Transactional
    fun signUp(signUpDto: SignUpDto): UserDto?
}