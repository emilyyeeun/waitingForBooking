package me.yeeunhong.waitingforbooking.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

interface WebSecurityConfigInterface {

    fun webSecurityCustomizer() : WebSecurityCustomizer
    @Throws(Exception::class) // java에서의 exception을 강제하기 위함
    fun filterChain(http: HttpSecurity) : SecurityFilterChain
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder
}