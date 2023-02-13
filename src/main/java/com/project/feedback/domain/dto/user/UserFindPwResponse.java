package com.project.feedback.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserFindPwResponse {
    private String userName;
    private String message;
    public static UserFindPwResponse of(String userName){
        return UserFindPwResponse.builder()
                .userName(userName)
                .message(userName + "님의 비밀번호가 변경되었습니다.")
                .build();
    }
}
