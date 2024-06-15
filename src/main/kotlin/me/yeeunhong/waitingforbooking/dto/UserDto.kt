package me.yeeunhong.waitingforbooking.dto


import me.yeeunhong.waitingforbooking.domain.User

data class UserDto (
    val id: Long,
    val username: String) {
    companion object {
        fun toDto(user: User): UserDto {
            return UserDto(user.id, user.username)
        }
    }
}