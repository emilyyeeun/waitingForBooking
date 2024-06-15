package me.yeeunhong.waitingforbooking.jwt

import org.springframework.security.core.Authentication

interface JwtTokenProviderInterface {
    fun generateToken(authentication: Authentication) : JwtToken
    fun getAuthentication(accessToken: String) : Authentication
    fun validateToken(token: String) : Boolean

}