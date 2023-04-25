package com.project.feedback.domain.dao;

import com.project.feedback.infra.outgoing.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserDAO {

    UserEntity insertUser(UserEntity user);

    Optional<UserEntity> findUserById(Long userId);


    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findByUserName(Pageable pageable, String userName);

    Page<UserEntity> findByRealName(Pageable pageable, String realName);

}
