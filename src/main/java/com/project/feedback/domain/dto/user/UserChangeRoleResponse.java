package com.project.feedback.domain.dto.user;

import com.project.feedback.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserChangeRoleResponse {
    private Long userId;
    private String message;

    public static UserChangeRoleResponse of(Long userId, Role newRole) {
        return UserChangeRoleResponse.builder()
                .userId(userId)
                .message("해당 유저 권한 " + newRole.name() + "으로 변경 완료")
                .build();
    }
}
