package me.yeeunhong.waitingforbooking.jwt

import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

interface JwtAuthenticationFilterInterface {
    @Throws(IOException::class, ServletException::class)
    fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain)
}