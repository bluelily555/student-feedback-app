package com.project.feedback.domain.dto.user;

import com.project.feedback.domain.Role;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String password;
    private String realName;
    private String email;

    public UserEntity toEntity(String encodedPassword) {
        return UserEntity.builder()
                .realName(this.realName)
                .email(this.email)
                .userName(this.userName)
                .password(encodedPassword)
                .role(Role.ROLE_STUDENT)
                .build();
    }
}
