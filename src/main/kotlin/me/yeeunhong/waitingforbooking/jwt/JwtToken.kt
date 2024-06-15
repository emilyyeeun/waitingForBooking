package me.yeeunhong.waitingforbooking.jwt

data class JwtToken(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)