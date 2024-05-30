package me.yeeunhong.waitingforbooking.service;

import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.domain.User;
import me.yeeunhong.waitingforbooking.dto.AddUserRequest;
import me.yeeunhong.waitingforbooking.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
