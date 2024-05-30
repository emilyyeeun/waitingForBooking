package me.yeeunhong.waitingforbooking.service;

import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.domain.User;
import me.yeeunhong.waitingforbooking.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
username 을 가지고 사용자 정보를 조회하고 session에 저장될 사용자 주체 정보인 userDetails을 반환
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private User user;

    @Override
    public User loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(username));

        return user;
    }
    private List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return authorities;
    }

}
