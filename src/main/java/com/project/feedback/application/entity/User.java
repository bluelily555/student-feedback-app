package com.project.feedback.application.entity;

import com.project.feedback.infra.outgoing.jpa.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    private long id;
    private String realName;

    public static User fromEntity(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .realName(entity.getRealName())
                .build();
    }
}
