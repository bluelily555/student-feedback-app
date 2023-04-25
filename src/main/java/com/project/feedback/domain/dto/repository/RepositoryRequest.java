package com.project.feedback.domain.dto.repository;

import com.project.feedback.infra.outgoing.entity.RepositoryEntity;
import com.project.feedback.infra.outgoing.entity.UserEntity;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RepositoryRequest {
    private String name;
    private String address;

    public RepositoryEntity toEntity() {
        return RepositoryEntity.builder()
                .name(name)
                .address(address)
                .build();
    }

    public RepositoryEntity toEntity(UserEntity user) {
        return RepositoryEntity.builder()
                .name(name)
                .address(address)
                .user(user)
                .build();
    }
}
