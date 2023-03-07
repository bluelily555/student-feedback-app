package com.project.feedback.domain.dto.repository;

import com.project.feedback.domain.entity.RepositoryEntity;
import com.project.feedback.domain.entity.UserEntity;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RepositoryRequest {
    private String name;
    private String address;

    public RepositoryEntity toEntity(UserEntity user) {
        return RepositoryEntity.builder()
                .name(name)
                .address(address)
                .user(user)
                .build();
    }
}
