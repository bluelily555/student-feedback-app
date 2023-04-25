package com.project.feedback.repository;

import com.project.feedback.infra.outgoing.jpa.RepositoryEntity;
import com.project.feedback.infra.outgoing.jpa.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryRepository extends JpaRepository<RepositoryEntity, Long> {
    List<RepositoryEntity> findByUserOrderByModifiedDateDesc(UserEntity user);

    @Query("select r from RepositoryEntity r join fetch r.user u where r.name = :name")
    List<RepositoryEntity> findByName(String name);
}