package me.yeeunhong.waitingforbooking.jwt

import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {
    val currentUsername: String
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            return if (authentication == null || authentication.name == null) {
                throw RuntimeException("No authentication information available.")
            } else {
                authentication.name
            }
        }
}

