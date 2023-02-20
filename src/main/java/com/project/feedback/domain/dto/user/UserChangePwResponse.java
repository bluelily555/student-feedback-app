package com.project.feedback.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePwResponse {
    private String userName;
    private String message;

    public static UserChangePwResponse of(String userName){
        return UserChangePwResponse.builder()
                .userName(userName)
                .message(userName + "님의 비밀번호가 변경되었습니다.")
                .build();
    }
}
