package me.yeeunhong.waitingforbooking.service;

import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.domain.User;
import me.yeeunhong.waitingforbooking.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(username));
    }
}
