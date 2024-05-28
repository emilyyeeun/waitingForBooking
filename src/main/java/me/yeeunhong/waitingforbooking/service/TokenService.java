package me.yeeunhong.waitingforbooking.service;

import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.config.jwt.TokenProvider;
import me.yeeunhong.waitingforbooking.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if (!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected Token");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findByUserId(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
