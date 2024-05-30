package me.yeeunhong.waitingforbooking.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
유저별로 다른 권한을 부여하기 위한 enum 클래스
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("사용자"), ROLE_ADMIN("관리자");
    private final String label;
}
