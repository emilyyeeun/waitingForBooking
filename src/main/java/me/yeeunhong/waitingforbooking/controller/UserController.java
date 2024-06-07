package me.yeeunhong.waitingforbooking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yeeunhong.waitingforbooking.dto.SignInDto;
import me.yeeunhong.waitingforbooking.dto.SignUpDto;
import me.yeeunhong.waitingforbooking.dto.UserDto;
import me.yeeunhong.waitingforbooking.jwt.JwtToken;
import me.yeeunhong.waitingforbooking.jwt.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import me.yeeunhong.waitingforbooking.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController implements UserControllerInterface {

    private final UserService userService;

   @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();
        JwtToken jwtToken = userService.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test")
    public String test() {
       return SecurityUtil.getCurrentUsername();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        UserDto savedUserDto = userService.signUp(signUpDto);
        log.info("username = {}, password = {}", signUpDto.getUsername(), signUpDto.getPassword());
        return ResponseEntity.ok(savedUserDto);
    }

}