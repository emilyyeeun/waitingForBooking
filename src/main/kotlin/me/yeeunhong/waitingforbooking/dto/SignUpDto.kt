package me.yeeunhong.waitingforbooking.dto

import me.yeeunhong.waitingforbooking.domain.User
data class SignUpDto(
    var username: String,
    val password:String,
    val roles: List<String> = ArrayList()
) {
    fun toEntity(encodedPassword: String, roles: List<String>): User {
        return User.builder()
            .username(username)
            .password(encodedPassword)
            .roles(roles)
            .build()
    }
}