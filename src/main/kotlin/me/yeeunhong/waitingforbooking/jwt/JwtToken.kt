package me.yeeunhong.waitingforbooking.jwt

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data

@Data
@AllArgsConstructor
@Builder
data class JwtToken(
    val grantType: String? = null,
    val accessToken: String? = null,
    val refreshToken: String? = null
)