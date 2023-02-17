package com.project.feedback.domain.dto.user;

import com.project.feedback.domain.Role;
import com.project.feedback.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserListDto {
    private Long id;
    private String userName;
    private String realName;
    private Role role;
    private String courseName;

    public static UserListDto of(UserEntity user, String courseName) {
        return UserListDto.builder()
            .id(user.getId())
            .userName(user.getUserName())
            .realName(user.getRealName())
            .role(user.getRole())
            .courseName(courseName)
            .build();
    }
}
