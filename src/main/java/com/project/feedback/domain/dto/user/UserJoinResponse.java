package com.project.feedback.domain.dto.user;

import com.project.feedback.infra.outgoing.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinResponse {
    private Long userId;
    private String userName;

    public static UserJoinResponse of(UserEntity user) {
        return UserJoinResponse.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .build();
    }
}
