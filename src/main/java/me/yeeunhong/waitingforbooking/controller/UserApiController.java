package me.yeeunhong.waitingforbooking.controller;

import lombok.RequiredArgsConstructor;
import me.yeeunhong.waitingforbooking.domain.User;
import me.yeeunhong.waitingforbooking.dto.AddUserRequest;
import me.yeeunhong.waitingforbooking.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect=:/login";
    }

    @GetMapping("/user")
    public User getUser(@PathVariable String username) {
        return userService.getUserbyUsername(username);
    }

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

}
