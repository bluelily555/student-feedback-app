package com.project.feedback.infra.outgoing.repository;

import com.project.feedback.infra.outgoing.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findByUserName(Pageable pageable, String userName);

    Page<UserEntity> findByRealName(Pageable pageable, String realName);
}
