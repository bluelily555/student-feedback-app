package com.project.feedback.domain.dto.repository;

import com.project.feedback.domain.entity.RepositoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RepositoryResponse {
    private Long id;
    private String name;
    private String address;

    public static RepositoryResponse of(RepositoryEntity entity) {
        return RepositoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }
}
