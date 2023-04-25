package com.project.feedback.domain.dao.Impl;

import com.project.feedback.domain.dao.UserDAO;
import com.project.feedback.infra.outgoing.entity.UserEntity;
import com.project.feedback.infra.outgoing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity insertUser(UserEntity user){
        UserEntity savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public Optional<UserEntity> findUserById(Long userId){
        Optional<UserEntity> selectedUser = userRepository.findById(userId);
        return selectedUser;
    }


    @Override
    public Optional<UserEntity> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<UserEntity> findByUserName(Pageable pageable, String userName) {
        return userRepository.findByUserName(pageable, userName);
    }

    @Override
    public Page<UserEntity> findByRealName(Pageable pageable, String realName) {
        return userRepository.findByRealName(pageable, realName);
    }
}
