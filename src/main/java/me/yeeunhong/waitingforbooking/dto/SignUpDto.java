package me.yeeunhong.waitingforbooking.dto;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import me.yeeunhong.waitingforbooking.domain.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class SignUpDto {
//    private String username;
//    private String password;
//    private List<String> roles = new ArrayList<>();
//
//    public User toEntity(String encodedPassword, List<String> roles) {
//        return User.builder()
//                .username(username)
//                .password(encodedPassword)
//                .roles(roles)
//                .build();
//    }
//}