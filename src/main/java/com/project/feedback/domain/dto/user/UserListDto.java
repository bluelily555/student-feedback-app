package com.project.feedback.domain.dto.user;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class UserListDto {
    private Long id;
    private String userName;
    private Role role;

    public static UserListDto of(User user) {
        return UserListDto.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .role(user.getRole())
            .build();
    }
}
