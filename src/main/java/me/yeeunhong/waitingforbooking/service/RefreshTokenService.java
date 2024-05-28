package me.yeeunhong.waitingforbooking.service;

import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.domain.RefreshToken;
import me.yeeunhong.waitingforbooking.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected Token"));
    }
}
